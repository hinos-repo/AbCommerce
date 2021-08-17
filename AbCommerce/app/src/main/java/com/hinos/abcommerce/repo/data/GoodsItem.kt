package com.hinos.abcommerce.repo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

@Entity(tableName = "tb_goods")
data class GoodsItem(
        @PrimaryKey @ColumnInfo(name = "id") // 상품 ID
        @SerializedName("id")
        val id : String = "",

        @ColumnInfo(name = "name")
        @SerializedName("name") // 상품 이름
        val name : String = "",

        @ColumnInfo(name = "image")
        @SerializedName("image") // 상품 이미지 url
        var image : String = "",

        @ColumnInfo(name = "actual_price")
        @SerializedName("actual_price") // 상품 기본 가격
        val actual_price : String = "",

        @ColumnInfo(name = "price")
        @SerializedName("price") // 상품 실제 가격 (기본 가격 X 할인율 / 100 == 실제가격)
        val price : String = "",

        @ColumnInfo(name = "is_new")
        @SerializedName("is_new") // 신상품이면 true
        val is_new : Boolean = false,

        @SerializedName("sell_count") // 구매중 갯수
        val sell_count : String = "",

        var is_like : Boolean = false
) {
        fun getSaleText() : String
        {
                val sale = (actual_price.toDouble() - price.toDouble()) * 100 / actual_price.toDouble()
                return "${sale.roundToInt()}%"
//                return "${((price.toInt() - actual_price.toInt()) / 100)} %"
        }

        fun getSellCountText() : String
        {
                return "$sell_count 개 구매중"
        }
}
