package com.hinos.abcommerce.repo.datasource

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.data.HomeDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface WebDataSource
{
    fun getHomeItems() : Single<HomeDTO>


    fun getMoreGoodsItems(lastId : String) : Single<MutableList<GoodsItem>>
}