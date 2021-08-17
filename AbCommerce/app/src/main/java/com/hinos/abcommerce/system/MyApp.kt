package com.hinos.abcommerce.system

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.facebook.stetho.Stetho
import com.hinos.abcommerce.repo.data.GoodsItem

import dagger.Module

@Module
class MyApp : Application()
{
    companion object {
        private var INSTANCE: MyApp? = null
        private val mocking = Any()

        fun getInstance(context: Context): MyApp {
            synchronized(mocking) {
                if (INSTANCE == null) {
                    INSTANCE = context.applicationContext as MyApp
                }
                return INSTANCE!!
            }
        }

        private val TAG = Application::class.java.simpleName
    }

    val mAllFavoriteEventLiveData = MutableLiveData<GoodsItem>()

    override fun onCreate()
    {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}