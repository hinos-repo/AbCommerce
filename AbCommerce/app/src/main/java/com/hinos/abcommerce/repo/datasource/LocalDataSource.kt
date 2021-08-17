package com.hinos.abcommerce.repo.datasource

import com.hinos.abcommerce.repo.data.GoodsItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource
{
    fun selectGoodsItems() : Single<MutableList<GoodsItem>>

    fun insertGoodsItem(item : GoodsItem) : Completable
}