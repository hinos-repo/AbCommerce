package com.hinos.abcommerce.repo.data

import com.google.gson.annotations.SerializedName

data class BannerItem(
        @SerializedName("id")
        val id : String = "",

        @SerializedName("image")
        val image : String = ""
)
