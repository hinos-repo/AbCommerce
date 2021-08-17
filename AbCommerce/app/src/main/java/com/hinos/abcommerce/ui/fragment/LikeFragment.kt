package com.hinos.abcommerce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hinos.abcommerce.R
import com.hinos.abcommerce.adapter.GoodsListAdapter
import com.hinos.abcommerce.databinding.FragmentLikeBinding
import com.hinos.abcommerce.factory.ViewModelFactory
import com.hinos.abcommerce.system.MyApp
import com.hinos.abcommerce.ui.main.MainViewModel

class LikeFragment : BaseFragment()
{
    private val TAG = LikeFragment::class.java.simpleName
    companion object
    {
        fun newInstance() : BaseFragment
        {
            return LikeFragment()
        }
    }

    private lateinit var mBinding : FragmentLikeBinding
    private lateinit var mMainViewModel : MainViewModel
    private lateinit var mAdtGoods : GoodsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_like, container, false)

        mMainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(MyApp.getInstance(getAct()))
        ).get(MainViewModel::class.java).apply {
            getFavoriteItems()
        }
        initViewSetting()
        observeFavoriteLiveData()
        observeAllFavoriteEventLiveData()

        return mBinding.root
    }

    private fun initViewSetting()
    {
        mBinding.vRecycleItem.run {
            layoutManager = GridLayoutManager(getAct(), 2, GridLayoutManager.VERTICAL, false)
            mAdtGoods = GoodsListAdapter(mMainViewModel.mOnFavoriteListener)
            adapter = mAdtGoods
        }
    }

    private fun observeFavoriteLiveData()
    {
        mMainViewModel.mFavoriteLiveData.observe(viewLifecycleOwner, {
            mAdtGoods.setGoodsNewData(it)
        })
    }

    private fun observeAllFavoriteEventLiveData()
    {
        MyApp.getInstance(getAct()).mAllFavoriteEventLiveData.observe(viewLifecycleOwner, {
            mMainViewModel.getFavoriteItems()
        })
    }
}