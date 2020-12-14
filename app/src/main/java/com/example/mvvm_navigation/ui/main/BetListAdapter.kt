package com.example.mvvm_navigation.ui.main

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.BetData

class BetListAdapter(
    val context: Context,
    val data: List<BetData>
) : ListAdapter<BetData, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<BetData>() {
        //檢查兩個物件是否是同一個對象，如果是，則不做任何操作，如果不是，則更新這個 Item。
        override fun areItemsTheSame(oldItem: BetData, newItem: BetData): Boolean {
            return oldItem === newItem
        }

        //檢查成員變數是否一樣來判斷是否要做任何操作，這裡可以依需求自行更換其他成員變數。
        override fun areContentsTheSame(oldItem: BetData, newItem: BetData): Boolean {
            return oldItem.score == newItem.score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolderUserItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_rv_item_tg_hot_sale,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewDataBinding = (holder as ViewHolderUserItem).binding
        viewDataBinding.setVariable(BR.score, data[position].score)
        viewDataBinding.setVariable(BR.hotSale, if(data[position].hotSale) View.VISIBLE else View.GONE)
        viewDataBinding.executePendingBindings()
    }

    private class ViewHolderUserItem(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}