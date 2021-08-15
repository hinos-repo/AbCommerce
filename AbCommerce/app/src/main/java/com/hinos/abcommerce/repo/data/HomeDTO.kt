package com.hinos.abcommerce.repo.data

import com.google.gson.annotations.SerializedName

data class HomeDTO(
        @SerializedName("banners")
        val mArBanners : MutableList<BannerItem> = mutableListOf(),
        @SerializedName("goods")
        val mArGoods : MutableList<GoodsItem> = mutableListOf()
)
