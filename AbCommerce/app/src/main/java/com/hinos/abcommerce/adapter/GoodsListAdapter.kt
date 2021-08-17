package com.hinos.abcommerce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hinos.abcommerce.databinding.ItemRowGoodsBinding
import com.hinos.abcommerce.listener.OnFavoriteListener
import com.hinos.abcommerce.repo.data.GoodsItem

class GoodsListAdapter(val mOnFavoriteListener : OnFavoriteListener)
    : RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder>()
{
    private val mArGoods: MutableList<GoodsItem> = mutableListOf()

    inner class GoodsViewHolder(private val viewBinding: ItemRowGoodsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindViewHolder(item: GoodsItem) {
            viewBinding.run {
                data = item
                callback = mOnFavoriteListener
                Glide.with(vIvProduct).load(item.image).into(vIvProduct)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        return GoodsViewHolder(
            ItemRowGoodsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        holder.bindViewHolder(mArGoods[position])
    }

    override fun getItemCount(): Int {
        return mArGoods.size
    }

    fun changeData(item : GoodsItem)
    {
        mArGoods.forEachIndexed { index, goodsItem ->
            if (item.id == goodsItem.id)
            {
                goodsItem.is_like = item.is_like
                this.notifyItemChanged(index)
            }
        }
    }

    fun setGoodsNewData(newData: MutableList<GoodsItem>) {
        this.calDiff(newData = newData)
    }

    private fun calDiff(newData: MutableList<GoodsItem>) {
        val diffCallback = DiffUtilGoodsCallback(this.mArGoods, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.mArGoods.clear()
        this.mArGoods.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffUtilGoodsCallback(
        private val oldData: MutableList<GoodsItem>,
        private val newData: MutableList<GoodsItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean // 먼저 호출
        {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }
    }
}