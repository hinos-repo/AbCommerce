package com.hinos.abcommerce.ui.main

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hinos.abcommerce.ui.fragment.BaseFragment
import com.hinos.abcommerce.ui.fragment.HomeFragment
import com.hinos.abcommerce.ui.fragment.LikeFragment
import com.hinos.abcommerce.R
import com.hinos.abcommerce.databinding.ActivityMainBinding
import com.hinos.abcommerce.factory.ViewModelFactory
import com.hinos.abcommerce.system.MyApp

class MainActivity : AppCompatActivity()
{

    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mMainViewModel : MainViewModel

    private val mPageFragments : MutableList<BaseFragment> = mutableListOf()
    private val mSelectColor = "#808080"
    private val mSelectNotColor = "#ff0000"
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        mMainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(MyApp.getInstance(this))
        ).get(MainViewModel::class.java).apply {
            createPageFragment()
            connectViewPager(supportFragmentManager, mPageFragments)
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.run {
            mainViewModel = mMainViewModel
        }

        observeSelectPosLvData()
    }

    private fun createPageFragment()
    {
        mPageFragments.run {
            clear()
            add(HomeFragment.newInstance())
            add(LikeFragment.newInstance())
        }
    }

    private fun selectPage(position : Int)
    {
        mBinding.run {
            vVpItem.currentItem = position
            if(position == 0)
            {
                vIvHome1.backgroundTintList = ColorStateList.valueOf(Color.parseColor(mSelectNotColor))
                vTvHome1.setTextColor(Color.parseColor(mSelectNotColor))
                vIvHome2.backgroundTintList = ColorStateList.valueOf(Color.parseColor(mSelectColor))
                vTvHome2.setTextColor(Color.parseColor(mSelectColor))
            } else {
                vIvHome1.backgroundTintList = ColorStateList.valueOf(Color.parseColor(mSelectColor))
                vTvHome1.setTextColor(Color.parseColor(mSelectColor))
                vIvHome2.backgroundTintList = ColorStateList.valueOf(Color.parseColor(mSelectNotColor))
                vTvHome2.setTextColor(Color.parseColor(mSelectNotColor))
            }
        }
    }

    private fun observeSelectPosLvData()
    {
        mMainViewModel.run {
            mSelectedPosLiveData.observe(this@MainActivity, {
                selectPage(it)
            })
        }
    }
}