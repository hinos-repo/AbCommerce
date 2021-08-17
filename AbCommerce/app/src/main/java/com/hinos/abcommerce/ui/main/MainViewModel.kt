package com.hinos.abcommerce.ui.main

import android.app.Application
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.hinos.abcommerce.adapter.ViewPagerAdapter
import com.hinos.abcommerce.listener.OnFavoriteListener
import com.hinos.abcommerce.repo.Repository
import com.hinos.abcommerce.repo.data.BannerItem
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.system.MyApp
import com.hinos.abcommerce.ui.fragment.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mApplication: Application, private var mRepository : Repository) : AndroidViewModel(mApplication)
{

//    s.
//
//    mAppComponent = DaggerAppComponent.builder().roomModule(RoomModule(this)).build()
//    mAppComponent.inject(this)

    private val TAG = this::class.java.simpleName
    private val BANNER_TIMER_INTERVAL : Long = 10 * 1000

    val mBannerLiveData = MutableLiveData<MutableList<BannerItem>>()
    val mGoodsLiveData = MutableLiveData<MutableList<GoodsItem>>()
    val mFavoriteLiveData = MutableLiveData<MutableList<GoodsItem>>()

    val mRefreshLiveData = MutableLiveData<Boolean>(false)
    val mBannerChangePositionLiveData = MutableLiveData<Boolean>(false)
    val mWaitProgress = MutableLiveData<Boolean>(false)
    val mBannerTimerLiveData = MutableLiveData<Int>(0)


    private val mCompositeDisposable = CompositeDisposable()
    private var mBannerDisposable : Disposable? = null
    private var mUsePaging = true
    private var mUseBannerScrolling = false
    lateinit var mAdtPage : ViewPagerAdapter

    val mSelectedPosLiveData : MutableLiveData<Int> = MutableLiveData(0) // 뷰페이저

    fun connectViewPager(fm: FragmentManager, pageFragments: MutableList<BaseFragment>)
    {
        mAdtPage = ViewPagerAdapter(fm, pageFragments)
    }

    fun onSelectPage(v: View, position: Int)
    {
        mSelectedPosLiveData.postValue(position)
    }

    override fun onCleared()
    {
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
        stopBannerTimer()
    }

    val mOnPageChangeListener = object : ViewPager.OnPageChangeListener
    {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
        {

        }

        override fun onPageSelected(position: Int)
        {
            mSelectedPosLiveData.value = position
        }

        override fun onPageScrollStateChanged(state: Int)
        {

        }
    }


    val mOnBannerScrollChangeListener = object : RecyclerView.OnScrollListener()
    {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE)
            {
                mBannerChangePositionLiveData.postValue(true)
            }
            mUseBannerScrolling = newState == RecyclerView.SCROLL_STATE_DRAGGING
        }
    }

    val mOnNestedScrollListener = NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        val view = v.getChildAt(v.childCount - 1) as View
        val diff: Int = view.bottom - (v.height + v.scrollY)
        if(diff == 0)
        {
            getMoreGoodsItems()
        }
    }

    val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        mRefreshLiveData.postValue(true)
        getHomeList()
    }

    fun getHomeList()
    {
        val singleHomeItems = mRepository.getHomeItems()
        val singleFavoriteItems = mRepository.selectFavoriteItems()

        mCompositeDisposable.add(Single.zip(singleHomeItems, singleFavoriteItems, { homeDTO, favoriteItems ->
            mBannerLiveData.postValue(homeDTO.mArBanners)
            homeDTO.mArGoods.map { goods ->
                favoriteItems.forEach { favItem ->
                    if (goods.id == favItem.id) goods.is_like = favItem.is_like
                }
            }
            return@zip homeDTO.mArGoods
        }).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                mGoodsLiveData.postValue(it)
                stopBannerTimer()
                startBannerTimer()
            }, {
                mGoodsLiveData.postValue(null)
            }))
        mUsePaging = true
        mRefreshLiveData.postValue(false)
        mWaitProgress.postValue(true)
    }

    private fun getMoreGoodsItems()
    {
        if (!mUsePaging)
            return

        val id = mGoodsLiveData.value?.let {
            it[it.size - 1].id
        } ?: return


        val singleMoreItems = mRepository.getMoreItems(id)
        val singleFavoriteItems = mRepository.selectFavoriteItems()

        mCompositeDisposable.add(Single.zip(singleMoreItems, singleFavoriteItems, { goodsItems, favoriteItems ->
            goodsItems.map { goods ->
                favoriteItems.forEach { favItem ->
                    if (goods.id == favItem.id) goods.is_like = favItem.is_like
                }
            }
            return@zip goodsItems
        }).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { newData ->
                mGoodsLiveData.value?.let { oldData ->
                    if (newData.isEmpty())
                    {
                        mUsePaging = false
                        mWaitProgress.postValue(false)
                        return@subscribe
                    }
                    oldData.addAll(newData)
                    mGoodsLiveData.postValue(oldData)
                }
            }, {
                mGoodsLiveData.postValue(null)
            }))
    }

    val mOnFavoriteListener = object : OnFavoriteListener
    {
        override fun onClickFavorite(v: View, item: GoodsItem)
        {
            item.is_like = !item.is_like
            insertFavorite(item)

            mGoodsLiveData.value?.map {
                if (it.id == item.id)
                {
                    it.is_like = item.is_like
                    return@map
                }
            }
        }
    }

    fun insertFavorite(item : GoodsItem)
    {
        mCompositeDisposable.add(
            mRepository.insertFavoriteItem(item)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    MyApp.getInstance(mApplication).mAllFavoriteEventLiveData.postValue(item)
                }, {
                    Log.d(TAG, "onClickFavorite: ${it.localizedMessage}")
                })
        )
    }

    fun getFavoriteItems()
    {
        mCompositeDisposable.add(
            mRepository.selectFavoriteItems().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mFavoriteLiveData.postValue(it)
                }, {
                    mFavoriteLiveData.postValue(null)
                })
        )
    }

    fun startBannerTimer()
    {
        mBannerDisposable = Observable.interval(BANNER_TIMER_INTERVAL, TimeUnit.MILLISECONDS).subscribe({
            if (!mUseBannerScrolling)
            {
                mBannerTimerLiveData.postValue(mBannerLiveData.value?.size ?: 0)
            }
        }, {

        })
    }

    fun stopBannerTimer()
    {
        mBannerDisposable?.dispose()
    }
}