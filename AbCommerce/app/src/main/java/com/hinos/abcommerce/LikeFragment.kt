package com.hinos.abcommerce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hinos.abcommerce.databinding.FragmentHomeBinding
import com.hinos.abcommerce.databinding.FragmentLikeBinding

class LikeFragment : BaseFragment()
{
    private lateinit var mBinding : FragmentLikeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_like, container, false)
        return mBinding.root
    }

    companion object
    {
        fun newInstance() : BaseFragment
        {
            return LikeFragment()
        }
    }
}