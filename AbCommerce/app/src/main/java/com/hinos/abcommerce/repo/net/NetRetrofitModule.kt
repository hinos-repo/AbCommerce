package com.hinos.abcommerce.repo.net

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object NetRetrofitModule
{
    private const val mBaseUrl = "http://d2bab9i9pr8lds.cloudfront.net/api/"

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit
    {
        return  Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient
    {
        return OkHttpClient.Builder().apply {

        }.build()
    }

    @Singleton
    @Provides
    fun provideRetroService(retrofit : Retrofit) : RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }


    //    private val mGson = GsonBuilder()
//        .setLenient()
//        .create()
}