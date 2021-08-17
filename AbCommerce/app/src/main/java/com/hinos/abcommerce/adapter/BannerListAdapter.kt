package com.hinos.abcommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hinos.abcommerce.R
import com.hinos.abcommerce.databinding.ItemBannerBinding
import com.hinos.abcommerce.repo.data.BannerItem

class BannerListAdapter : RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>()
{
    private val mArBanner : MutableList<BannerItem> = mutableListOf()
    class BannerViewHolder(private val viewBinding : ItemBannerBinding) : RecyclerView.ViewHolder(viewBinding.root)
    {
        fun bindViewHolder(item : BannerItem)
        {
            viewBinding.run {
                Glide.with(vIvBanner).load(item.image).into(vIvBanner)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder
    {
        return BannerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int)
    {
        holder.bindViewHolder(mArBanner[position])
    }

    override fun getItemCount(): Int
    {
        return mArBanner.size
    }

    fun setNewData(newData : MutableList<BannerItem>)
    {
        this.calDiff(newData = newData)
    }

    private fun calDiff(newData : MutableList<BannerItem>)
    {
        val diffCallback = DiffUtilBannerCallback(this.mArBanner, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.mArBanner.clear()
        this.mArBanner.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffUtilBannerCallback(
            private val oldData : MutableList<BannerItem>,
            private val newData : MutableList<BannerItem>
    ) : DiffUtil.Callback()
    {
        override fun getOldListSize(): Int
        {
            return oldData.size
        }

        override fun getNewListSize(): Int
        {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean // 먼저 호출
        {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }
    }
}