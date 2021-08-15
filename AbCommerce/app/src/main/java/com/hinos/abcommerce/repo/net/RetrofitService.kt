package com.hinos.abcommerce.repo.net

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.data.HomeDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RetrofitService
{
    @GET("home")
    fun getHomeItems() : Single<HomeDTO>

    @GET("home/goods")
    fun getMoreGoodsItems(@Query("lastId") lastId : String) : Single<MutableList<GoodsItem>>

}