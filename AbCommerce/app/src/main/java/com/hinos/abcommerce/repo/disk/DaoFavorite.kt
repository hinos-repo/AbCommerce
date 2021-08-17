package com.hinos.abcommerce.repo.disk

import androidx.room.*
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.util.Const
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface DaoFavorite
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: GoodsItem) : Completable

    @Delete
    fun delete(item: GoodsItem) : Completable

    @Query("DELETE FROM tb_goods")
    fun deleteAllData() : Completable

    @Query("SELECT * FROM tb_goods WHERE is_like == ${Const.LIKE_YES}")
    fun getAllFavoriteItems(): Single<MutableList<GoodsItem>>
}