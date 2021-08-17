package com.hinos.abcommerce.factory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hinos.abcommerce.di.DaggerViewModelComponent

import com.hinos.abcommerce.di.ViewModelComponent
import com.hinos.abcommerce.di.disk.RoomModule
import com.hinos.abcommerce.repo.Repository
import com.hinos.abcommerce.system.MyApp
import com.hinos.abcommerce.ui.main.MainViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ViewModelFactory(private val mContext: Context) : ViewModelProvider.Factory
{
    @Inject
    lateinit var mRepository: Repository

    init {
        val mComponent : ViewModelComponent =
            DaggerViewModelComponent
                .builder()
                .roomModule(RoomModule(
                    MyApp.getInstance(mContext)
                ))
                .build()

        mComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(MyApp.getInstance(mContext), mRepository) as T
        else {
            throw IllegalArgumentException("Unknwon ViewModel Class")
        }
    }
}