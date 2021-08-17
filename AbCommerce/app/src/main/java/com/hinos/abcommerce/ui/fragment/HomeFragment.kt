package com.hinos.abcommerce.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hinos.abcommerce.R
import com.hinos.abcommerce.adapter.BannerListAdapter
import com.hinos.abcommerce.adapter.GoodsListAdapter
import com.hinos.abcommerce.databinding.FragmentHomeBinding
import com.hinos.abcommerce.system.MyApp

class HomeFragment : BaseFragment()
{
    companion object
    {
        fun newInstance() : BaseFragment
        {
            return HomeFragment()
        }
    }

    private lateinit var mBinding : FragmentHomeBinding
    private lateinit var mMgrLinearLayout : LinearLayoutManager
    private var mStopLifecycle = false
    private val mAdtBanner : BannerListAdapter by lazy {
        BannerListAdapter()
    }
    private lateinit var mAdtGoods : GoodsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initViewSetting()

        observeBannerLiveData()
        observeBannerScrollLiveData()
        observeGoodsLiveData()
        observeFavoriteLiveData()
        observeRefreshLiveData()
        observeWaitProgressLiveData()
        observeBannerTimerLiveData()
        mMainViewModel.getHomeList()
        return mBinding.root
    }

    private fun initViewSetting()
    {
        mBinding.vRecycleBanner.run {
            mMgrLinearLayout = LinearLayoutManager(getAct(), LinearLayoutManager.HORIZONTAL, false)
            layoutManager = mMgrLinearLayout
            adapter = mAdtBanner
            PagerSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(mMainViewModel.mOnBannerScrollChangeListener)

        }

        mBinding.vRecycleItem.run {
            layoutManager = GridLayoutManager(getAct(), 2, GridLayoutManager.VERTICAL, false)
            mAdtGoods = GoodsListAdapter(mMainViewModel.mOnFavoriteListener)
            adapter = mAdtGoods
        }

        mBinding.run {
            vNsvScroll.setOnScrollChangeListener(mMainViewModel.mOnNestedScrollListener)
            vSrlRefresh.setOnRefreshListener(mMainViewModel.mOnRefreshListener)
        }

    }

    private fun observeBannerLiveData()
    {
        mMainViewModel.mBannerLiveData.observe(viewLifecycleOwner, {
            mAdtBanner.setNewData(it)
            setListCount()
        })
    }

    private fun observeBannerScrollLiveData()
    {
        mMainViewModel.mBannerChangePositionLiveData.observe(viewLifecycleOwner, {
            if (it)
            {
                setListCount()
            }
        })
    }

    private fun observeGoodsLiveData()
    {
        mMainViewModel.mGoodsLiveData.observe(viewLifecycleOwner, {
            mAdtGoods.setGoodsNewData(it)
        })
    }

    private fun observeFavoriteLiveData()
    {
        MyApp.getInstance(getAct()).mAllFavoriteEventLiveData.observe(viewLifecycleOwner, {
            mAdtGoods.changeData(it)
        })
    }

    private fun observeRefreshLiveData()
    {
        mMainViewModel.mRefreshLiveData.observe(viewLifecycleOwner, {
            mBinding.run {
                vSrlRefresh.isRefreshing = it
            }
        })
    }

    private fun observeWaitProgressLiveData()
    {
        mMainViewModel.mWaitProgress.observe(viewLifecycleOwner, {
            mBinding.run {
                vLlProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    private fun observeBannerTimerLiveData()
    {
        mMainViewModel.mBannerTimerLiveData.observe(viewLifecycleOwner, {
            mBinding.run {
                val currentPos = mMgrLinearLayout.findFirstVisibleItemPosition()
                val nPos = if (it == 0 || currentPos >= it-1) {
                    0
                } else {
                    currentPos+1
                }


                vRecycleBanner.smoothScrollToPosition(nPos)
                Handler(Looper.getMainLooper()).postDelayed({
                    setListCount()
                }, 150)
            }
        })
    }

    private fun setListCount()
    {
        if (mStopLifecycle)
            return

        mBinding.run {
            val currentPosition = mMgrLinearLayout.findFirstVisibleItemPosition().let {
                if (it <= 0) 1 else it+1
            }
            vTvBannerCount.text = "$currentPosition / ${mAdtBanner.itemCount}"
        }
    }

    override fun onResume()
    {
        super.onResume()
        mStopLifecycle = false
    }

    override fun onStop()
    {
        super.onStop()
        mStopLifecycle = true
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mBinding.run {
            vRecycleBanner.removeOnScrollListener(mMainViewModel.mOnBannerScrollChangeListener)
        }
    }
}