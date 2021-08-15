package com.hinos.abcommerce.repo.datasource

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.disk.DaoGoods
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mDaoGoods : DaoGoods)
{
    fun selectGoodsItems() : Single<MutableList<GoodsItem>>
    {
        return mDaoGoods.selectGoodsItems()
    }

    fun insertGoodsItem(item : GoodsItem) : Completable
    {
        return mDaoGoods.insert(item)
    }
}