package com.hinos.abcommerce.repo.datasource
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.data.HomeDTO
import com.hinos.abcommerce.di.net.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WebDataSourceImpl constructor(private val mRetrofitService: RetrofitService) : WebDataSource
{
    override fun getHomeItems() : Single<HomeDTO>
    {
        return mRetrofitService
            .getHomeItems()
            .subscribeOn(Schedulers.io())
    }

    override fun getMoreGoodsItems(lastId : String) : Single<MutableList<GoodsItem>>
    {
        return mRetrofitService
            .getMoreGoodsItems(lastId)
            .subscribeOn(Schedulers.io())
            .map { it.mArGoods }
    }
}