package com.hinos.abcommerce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hinos.abcommerce.R
import com.hinos.abcommerce.databinding.FragmentHomeBinding

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

}