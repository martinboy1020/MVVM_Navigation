package com.example.mvvm_navigation.ui.match.matchlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.utils.GameStatusUtils
import kotlinx.android.synthetic.main.layout_rv_match_item.view.*

class MatchListAdapter(
    val context: Context,
    var data: MutableList<MatchList.Match>,
    private val listener: MatchListAdapterItemClickListener? = null
) : ListAdapter<MatchList.Match, MatchListAdapter.MatchListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MatchList.Match>() {
        override fun areItemsTheSame(oldItem: MatchList.Match, newItem: MatchList.Match): Boolean {
            return oldItem == newItem
        }

        //判斷兩個Objects 是否有相同的內容
        override fun areContentsTheSame(
            oldItem: MatchList.Match,
            newItem: MatchList.Match
        ): Boolean {
            return oldItem.matchId == newItem.matchId
        }
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    interface MatchListAdapterItemClickListener {
        fun onSetTopClick(data: MatchList.Match)
        fun onClickItem(data: MatchList.Match)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MatchListViewHolder(inflater.inflate(R.layout.layout_rv_match_item, parent, false))
    }

    override fun onCurrentListChanged(
        previousList: MutableList<MatchList.Match>,
        currentList: MutableList<MatchList.Match>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }

    override fun onBindViewHolder(holder: MatchListViewHolder, position: Int) {
        holder.homeName.text = data[position].homeName
        holder.awayName.text = data[position].awayName
        if (data[position].isTopOfList) {
            holder.imgTop.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
            holder.homeName.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_light
                )
            )
            holder.awayName.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_light
                )
            )
        } else {
            holder.imgTop.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
            holder.homeName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            holder.awayName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }

        if (GameStatusUtils.MatchStatus.checkGameMatchIsStarted(data[position].status)
            || data[position].status == GameStatusUtils.MatchStatus.ENDING
        ) {
            holder.score.text =
                String.format("%d-%d", data[position].homeScore, data[position].awayScore)
            holder.matchInfo.text =
                String.format(
                    "半%d-%d 角%d-%d",
                    data[position].homeHalfScore,
                    data[position].awayHalfScore,
                    data[position].homeCorner,
                    data[position].awayCorner
                )
        }

        holder.matchStatus.text = GameStatusUtils.MatchStatus.getGameStatusString(context, data[position].status)

        holder.openDate.text =
            DateUtils.convertTimestampToStringDate(data[position].openDate.toInt(), DateUtils.HHMM)
        holder.favoritePerson.text = data[position].betsCount.toString()
        holder.bg.setOnClickListener { listener?.onSetTopClick(data[position]) }
    }

    fun setList(data: MutableList<MatchList.Match>) {
        this.data = data
        notifyDataSetChanged()
    }

    class MatchListViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        var homeName: TextView = root.tv_home_team_name
        var awayName: TextView = root.tv_away_team_name
        var bg: ConstraintLayout = root.layout_match_item
        var imgTop: ImageView = root.img_top
        var openDate: TextView = root.tv_match_start_time
        var favoritePerson: TextView = root.tv_favorite_person
        var score: TextView = root.tv_score
        var matchInfo: TextView = root.tv_match_info
        var matchStatus: TextView = root.tv_match_status
    }

}