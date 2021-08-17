package com.hinos.abcommerce.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.hinos.abcommerce.ui.main.MainActivity

open class BaseFragment : Fragment()
{
    private var mActivity : MainActivity? = null
    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mActivity = context as MainActivity
    }

    fun getAct() : MainActivity
    {
        mActivity?.let {
            return it
        } ?: apply {
            mActivity = activity as MainActivity
        }
        mActivity ?: throw IllegalAccessException("BaseFragment context is null")
        return mActivity!!
    }
}