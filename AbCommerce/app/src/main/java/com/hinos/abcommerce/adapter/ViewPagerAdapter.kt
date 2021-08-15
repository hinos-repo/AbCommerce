package com.hinos.abcommerce.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hinos.abcommerce.ui.fragment.BaseFragment

class ViewPagerAdapter(
    fm: FragmentManager, private val mTabFragment : MutableList<BaseFragment>)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    override fun getCount(): Int
    {
        return mTabFragment.size
    }

    override fun getItem(position: Int): BaseFragment
    {
        return mTabFragment[position]
    }
}