package com.hinos.abcommerce.repo.disk

import androidx.room.*
import com.hinos.abcommerce.repo.data.GoodsItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface DaoGoods
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: GoodsItem) : Completable

    @Delete
    fun delete(item: GoodsItem) : Completable

    @Query("DELETE FROM tb_goods")
    fun deleteAllData() : Completable

    @Query("SELECT * FROM tb_goods")
    fun selectGoodsItems(): Single<MutableList<GoodsItem>>
}