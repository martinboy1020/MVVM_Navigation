package com.example.mvvm_navigation.ui.main.home

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.widget.ImageShapeWidget
import kotlinx.android.synthetic.main.layout_rv_recent_match_item.view.*

class MatchesRecentAdapter(
    val context: Context,
    val data: List<TgMatchRecent.Recent>,
    private val listener: MatchListAdapterItemClickListener? = null
) : ListAdapter<TgMatchRecent.Recent, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<TgMatchRecent.Recent>() {
        //檢查兩個物件是否是同一個對象，如果是，則不做任何操作，如果不是，則更新這個 Item。
        override fun areItemsTheSame(
            oldItem: TgMatchRecent.Recent,
            newItem: TgMatchRecent.Recent
        ): Boolean {
            return oldItem === newItem
        }

        //檢查成員變數是否一樣來判斷是否要做任何操作，這裡可以依需求自行更換其他成員變數。
        override fun areContentsTheSame(
            oldItem: TgMatchRecent.Recent,
            newItem: TgMatchRecent.Recent
        ): Boolean {
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
                R.layout.layout_rv_recent_match_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewDataBinding = (holder as ViewHolderUserItem).binding
        holder.homeLogo.setImage(data[position].homeLogo)
        holder.awayLogo.setImage(data[position].awayLogo)
        holder.leagueName.text = data[position].league
        if (data[position].isTopOfList) {
            holder.imgTop.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
        } else {
            holder.imgTop.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
        }
        holder.homeName.text = data[position].home
        holder.awayName.text = data[position].away
        holder.imgTop.setOnClickListener { listener?.onSetTopClick(data[position]) }
        holder.openDate.text = DateUtils.convertTimestampToStringDate(data[position].openDate.toInt(), DateUtils.HHMM)
        viewDataBinding.root.layout_recent_match_item.setOnClickListener { listener?.onClickItem(data[position]) }
        viewDataBinding.executePendingBindings()
    }

    private class ViewHolderUserItem(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val homeLogo: ImageShapeWidget = binding.root.findViewById(R.id.img_home_logo)
        val awayLogo: ImageShapeWidget = binding.root.findViewById(R.id.img_away_logo)
        val homeName: TextView = binding.root.findViewById(R.id.tv_home_team_name)
        val awayName: TextView = binding.root.findViewById(R.id.tv_away_team_name)
        val leagueName: TextView = binding.root.findViewById(R.id.tv_league_name)
        val imgTop: ImageView = binding.root.findViewById(R.id.img_top)
        val openDate: TextView = binding.root.findViewById(R.id.tv_match_start_time)
        val countDown: TextView = binding.root.findViewById(R.id.tv_match_count_down_time)
    }
}