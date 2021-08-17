package com.hinos.abcommerce.repo.datasource

import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.di.disk.DaoFavorite
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val mDaoFavorite : DaoFavorite) : LocalDataSource
{
    override fun selectGoodsItems(): Single<MutableList<GoodsItem>> {
        return mDaoFavorite.getAllFavoriteItems()

    }
    override fun insertGoodsItem(item: GoodsItem): Completable {
        return mDaoFavorite.insert(item)
    }
}