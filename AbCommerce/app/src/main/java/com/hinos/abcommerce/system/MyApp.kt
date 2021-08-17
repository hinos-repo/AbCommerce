package com.hinos.abcommerce.system

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.facebook.stetho.Stetho
import com.hinos.abcommerce.repo.Repository
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.datasource.LocalDataSource
import com.hinos.abcommerce.repo.datasource.WebDataSource
import com.hinos.abcommerce.repo.disk.AppDatabase
import com.hinos.abcommerce.repo.net.AppComponent
import com.hinos.abcommerce.repo.net.DaggerAppComponent
import com.hinos.abcommerce.repo.net.RetrofitService
import javax.inject.Inject

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

        private val TAG = MyApp::class.java.simpleName
    }

    lateinit var mAppComponent : AppComponent

    @Inject
    lateinit var mRetrofitService: RetrofitService

    lateinit var mRepository : Repository

    val mAllFavoriteEventLiveData = MutableLiveData<GoodsItem>()

    override fun onCreate()
    {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        initComponent()
    }

    private fun initComponent()
    {
        mAppComponent = DaggerAppComponent.builder().build()
        mAppComponent.inject(this)

        mRepository = Repository(
            WebDataSource(mRetrofitService),
            LocalDataSource(AppDatabase.getInstance(this).goodsDao())
        )
    }
}