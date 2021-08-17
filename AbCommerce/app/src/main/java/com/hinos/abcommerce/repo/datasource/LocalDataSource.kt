package com.hinos.abcommerce.repo.datasource

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.disk.DaoFavorite
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocalDataSource constructor(private val mDaoGoods : DaoFavorite)
{
    fun selectGoodsItems() : Single<MutableList<GoodsItem>>
    {
        return mDaoGoods.getAllFavoriteItems()
    }

    fun insertGoodsItem(item : GoodsItem) : Completable
    {
        return mDaoGoods.insert(item)
    }
}