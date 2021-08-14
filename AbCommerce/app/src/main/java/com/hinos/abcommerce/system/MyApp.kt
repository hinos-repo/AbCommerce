package com.hinos.abcommerce.system

import android.app.Application
import android.content.Context

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
    }

    override fun onCreate()
    {
        super.onCreate()
    }
}