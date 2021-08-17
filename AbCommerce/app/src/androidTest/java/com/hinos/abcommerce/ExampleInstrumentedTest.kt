package com.hinos.abcommerce

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hinos.abcommerce.factory.ViewModelFactory
import com.hinos.abcommerce.system.MyApp
import com.hinos.abcommerce.ui.main.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest
{
    private val TIME_OUT : Long = 2 * 1000

    lateinit var mContext : Context

    @Before
    fun useAppContext() {
        // Context of the app under test.
        mContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getHomeDTO()
    {
        MyApp.getInstance(mContext).mRepository.getHomeItems().observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals(it.mArBanners.size, 3)
                assertEquals(it.mArGoods.size, 10)
            }, {
                AssertionError(it.localizedMessage)
            })

        Thread.sleep(TIME_OUT)
    }

    @Test
    fun getMoreItem()
    {
        MyApp.getInstance(mContext).mRepository.getMoreItems("10").observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals(it[0].id, "11")
            }, {
                AssertionError(it.localizedMessage)
            })

        Thread.sleep(TIME_OUT)
    }
}