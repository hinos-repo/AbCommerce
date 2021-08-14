package com.hinos.abcommerce

import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.hinos.abcommerce.adapter.ViewPagerAdapter
import com.hinos.abcommerce.system.MyApp
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainViewModel (private val app: MyApp) : AndroidViewModel(app)
{
    private val mCompositeDisposable = CompositeDisposable()

    lateinit var mPagerAdapter : ViewPagerAdapter

    val mSelectedPosLiveData : MutableLiveData<Int> = MutableLiveData(0)

    fun connectViewPager(fm : FragmentManager, pageFragments : MutableList<BaseFragment>)
    {
        mPagerAdapter = ViewPagerAdapter(fm , pageFragments)
    }

    fun onSelectPage(v : View, position : Int)
    {
        mSelectedPosLiveData.postValue(position)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
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

}