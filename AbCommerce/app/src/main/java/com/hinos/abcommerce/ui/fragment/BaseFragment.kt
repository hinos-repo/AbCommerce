package com.hinos.abcommerce.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hinos.abcommerce.ui.main.MainActivity
import com.hinos.abcommerce.ui.main.MainViewModel

open class BaseFragment : Fragment()
{
    private var mActivity : MainActivity? = null
    protected lateinit var mMainViewModel : MainViewModel

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mActivity = context as MainActivity

        mMainViewModel = ViewModelProvider(getAct()).get(MainViewModel::class.java)
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