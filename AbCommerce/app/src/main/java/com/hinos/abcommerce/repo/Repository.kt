package com.hinos.abcommerce.repo

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.data.HomeDTO
import com.hinos.abcommerce.repo.datasource.LocalDataSource
import com.hinos.abcommerce.repo.datasource.WebDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository (private val webDataSource: WebDataSource, private val localDataSource : LocalDataSource)
{
    fun getHomeItems() : Single<HomeDTO>
    {
        return webDataSource.getHomeItems()
    }

    fun getMoreItems(lastId : String) : Single<MutableList<GoodsItem>>
    {
        return webDataSource.getMoreGoodsItems(lastId)
    }

    fun insertFavoriteItem(item: GoodsItem) : Completable
    {
        return localDataSource.insertGoodsItem(item)
    }

    fun selectFavoriteItems() : Single<MutableList<GoodsItem>>
    {
        return localDataSource.selectGoodsItems()
    }

}