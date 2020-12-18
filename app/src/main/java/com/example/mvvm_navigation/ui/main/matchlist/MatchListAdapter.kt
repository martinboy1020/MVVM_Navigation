package com.example.mvvm_navigation.ui.main.matchlist

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import kotlinx.android.synthetic.main.layout_rv_match_item.view.*

class MatchListAdapter(
    val context: Context,
    val data: List<MatchListItem>,
    private val listener: MatchListAdapterItemClickListener? = null
) : ListAdapter<MatchListItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MatchListItem>() {
        //檢查兩個物件是否是同一個對象，如果是，則不做任何操作，如果不是，則更新這個 Item。
        override fun areItemsTheSame(oldItem: MatchListItem, newItem: MatchListItem): Boolean {
            return oldItem === newItem
        }

        //檢查成員變數是否一樣來判斷是否要做任何操作，這裡可以依需求自行更換其他成員變數。
        override fun areContentsTheSame(oldItem: MatchListItem, newItem: MatchListItem): Boolean {
            return oldItem.matchId == newItem.matchId
        }
    }

    interface MatchListAdapterItemClickListener {
        fun onSetTopClick(data: MatchListItem)
        fun onClickItem(data: MatchListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolderUserItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_rv_match_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewDataBinding = (holder as ViewHolderUserItem).binding
        viewDataBinding.setVariable(BR.homeTeamName, data[position].homeName)
        viewDataBinding.setVariable(BR.awayTeamName, data[position].awayName)
        viewDataBinding.setVariable(
            BR.isTopListImg,
            if (data[position].isTopOfList) R.drawable.ic_baseline_keyboard_arrow_up else R.drawable.ic_baseline_keyboard_arrow_down
        )
        viewDataBinding.root.img_top.setOnClickListener {
            listener?.onSetTopClick(data[position])
        }
        viewDataBinding.root.layout_match_item.setOnClickListener{
            listener?.onClickItem(data[position])
        }
        viewDataBinding.executePendingBindings()
    }

    private class ViewHolderUserItem(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}