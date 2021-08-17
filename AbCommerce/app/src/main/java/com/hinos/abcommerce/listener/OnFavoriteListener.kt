package com.hinos.abcommerce.listener

import android.view.View
import com.hinos.abcommerce.repo.data.GoodsItem

interface OnFavoriteListener
{
    fun onClickFavorite(v : View, item : GoodsItem)
}