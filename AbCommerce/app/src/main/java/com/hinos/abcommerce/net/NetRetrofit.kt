package com.hinos.abcommerce.net

import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object NetRetrofit
{
    private val mBaseUrl = ""

    private val mGson = GsonBuilder()
        .setLenient()
        .create()

    private val mOkHttpClient = OkHttpClient.Builder().apply {

    }.build()

    private val mRetrofit = Retrofit.Builder()
        .baseUrl(mBaseUrl)
        .client(mOkHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(mGson))
        .build()

    private val mRetrofitService: RetrofitService = mRetrofit.create(
        RetrofitService::class.java
    )
    fun getService() : RetrofitService
    {
        return mRetrofitService
    }
}