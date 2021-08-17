package com.hinos.abcommerce.repo

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.data.HomeDTO
import com.hinos.abcommerce.repo.datasource.LocalDataSourceImpl
import com.hinos.abcommerce.repo.datasource.WebDataSourceImpl
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(private val mWebDataSource: WebDataSourceImpl, private val mLocalDataSource : LocalDataSourceImpl)
{
    fun getHomeItems() : Single<HomeDTO>
    {
        return mWebDataSource.getHomeItems()
    }

    fun getMoreItems(lastId : String) : Single<MutableList<GoodsItem>>
    {
        return mWebDataSource.getMoreGoodsItems(lastId)
    }

    fun insertFavoriteItem(item: GoodsItem) : Completable
    {
        return mLocalDataSource.insertGoodsItem(item)
    }

    fun selectFavoriteItems() : Single<MutableList<GoodsItem>>
    {
        return mLocalDataSource.selectGoodsItems()
    }

}