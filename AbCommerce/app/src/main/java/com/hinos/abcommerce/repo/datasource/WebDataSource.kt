package com.hinos.abcommerce.repo.datasource
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.repo.data.HomeDTO
import com.hinos.abcommerce.repo.net.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WebDataSource @Inject constructor(private val mRetrofitService: RetrofitService)
{
    fun getHomeItems() : Single<HomeDTO>
    {
        return mRetrofitService
            .getHomeItems()
            .subscribeOn(Schedulers.io())
    }

    fun getMoreGoodsItems(lastId : String) : Single<MutableList<GoodsItem>>
    {
        return mRetrofitService
            .getMoreGoodsItems(lastId)
            .subscribeOn(Schedulers.io())
    }
}