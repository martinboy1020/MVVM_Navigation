package com.example.mvvm_navigation.ui.main.home

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
import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import kotlinx.android.synthetic.main.layout_rv_match_item.view.*

class MatchesRecentAdapter(
    val context: Context,
    val data: List<TgMatchRecent.Recent>,
    private val listener: MatchListAdapterItemClickListener? = null
) : ListAdapter<TgMatchRecent.Recent, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<TgMatchRecent.Recent>() {
        //檢查兩個物件是否是同一個對象，如果是，則不做任何操作，如果不是，則更新這個 Item。
        override fun areItemsTheSame(oldItem: TgMatchRecent.Recent, newItem: TgMatchRecent.Recent): Boolean {
            return oldItem === newItem
        }

        //檢查成員變數是否一樣來判斷是否要做任何操作，這裡可以依需求自行更換其他成員變數。
        override fun areContentsTheSame(oldItem: TgMatchRecent.Recent, newItem: TgMatchRecent.Recent): Boolean {
            return oldItem.matchId == newItem.matchId
        }
    }

    interface MatchListAdapterItemClickListener {
        fun onSetTopClick(data: TgMatchRecent.Recent)
        fun onClickItem(data: TgMatchRecent.Recent)
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
//        viewDataBinding.setVariable(BR.homeTeamName, data[position].home)
//        viewDataBinding.setVariable(BR.awayTeamName, data[position].away)
        viewDataBinding.setVariable(
            BR.isTopListImg,
            if (data[position].isTopOfList) R.drawable.ic_baseline_keyboard_arrow_up else R.drawable.ic_baseline_keyboard_arrow_down
        )
        viewDataBinding.setVariable(BR.openDate, DateUtils.convertTimestampToStringDate(data[position].openDate.toInt(), DateUtils.HHMM))
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