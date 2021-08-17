package com.hinos.abcommerce.system

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.facebook.stetho.Stetho
import com.hinos.abcommerce.repo.Repository
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.datasource.LocalDataSourceImpl
import com.hinos.abcommerce.repo.datasource.WebDataSourceImpl
import com.hinos.abcommerce.di.AppComponent
import com.hinos.abcommerce.di.DaggerAppComponent
import com.hinos.abcommerce.di.disk.AppDatabase
import com.hinos.abcommerce.di.disk.DaoFavorite
import com.hinos.abcommerce.di.net.RetrofitService
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

//    @Inject
//    lateinit var mDaoFavorite: DaoFavorite

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
            WebDataSourceImpl(mRetrofitService),
            LocalDataSourceImpl(AppDatabase.getInstance(this).favoriteDao())
//            LocalDataSourceImpl(mDaoGoods)
        )
    }
}