package com.hinos.abcommerce.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hinos.abcommerce.ui.main.MainViewModel
import com.hinos.abcommerce.system.MyApp
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val myApp: MyApp
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(myApp) as T
        else {
            throw IllegalArgumentException("Unknwon ViewModel Class")
        }
    }
}