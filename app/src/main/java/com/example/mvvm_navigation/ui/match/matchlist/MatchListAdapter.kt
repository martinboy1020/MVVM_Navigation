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
import kotlinx.android.synthetic.main.layout_rv_match_item.view.*
import org.w3c.dom.Text

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
        override fun areContentsTheSame(oldItem: MatchList.Match, newItem: MatchList.Match): Boolean {
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
        holder.awayName.text = data[position].homeName
        if (data[position].isTopOfList) {
            holder.imgTop.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
            holder.homeName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
            holder.awayName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        } else {
            holder.imgTop.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
            holder.homeName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            holder.awayName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }
        holder.openDate.text = DateUtils.convertTimestampToStringDate(data[position].openDate.toInt(), DateUtils.HHMM)
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
    }

}

//class MatchListAdapter(
//    val context: Context,
//    private val listener: MatchListAdapterItemClickListener? = null
//) : ListAdapter<MatchList.Match, MatchListAdapter.MatchViewHolder>(DiffCallback) {
//
//    companion object DiffCallback : DiffUtil.ItemCallback<MatchList.Match>() {
//        // 返回兩個Object是否相同
//        // 例如：此處兩個Object的數據實體是MatchList.Match類，所以以matchId作為兩個Object是否相同的依據
//        // 即此處返回兩個MatchList.Match的matchId是否相同
//        override fun areItemsTheSame(oldItem: MatchList.Match, newItem: MatchList.Match): Boolean {
//            Log.d("tag12345", "areItemsTheSame")
//            return oldItem == newItem
//        }
//
//        //判斷兩個Objects 是否有相同的內容
//        override fun areContentsTheSame(
//            oldItem: MatchList.Match,
//            newItem: MatchList.Match
//        ): Boolean {
//            Log.d("tag12345", "areContentsTheSame")
//            return oldItem == newItem
//        }
//    }
//
//    interface MatchListAdapterItemClickListener {
//        fun onSetTopClick(data: MatchList.Match)
//        fun onClickItem(data: MatchList.Match)
//    }
//
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
////        ViewHolderUserItem(
////            DataBindingUtil.inflate(
////                LayoutInflater.from(parent.context),
////                R.layout.layout_rv_match_item,
////                parent,
////                false
////            )
////
////        )
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return MatchViewHolder(inflater.inflate(R.layout.layout_rv_match_item, parent, false))
//    }
//
//    override fun onCurrentListChanged(
//        previousList: MutableList<MatchList.Match>,
//        currentList: MutableList<MatchList.Match>
//    ) {
////        Log.d("tag12345", "MatchListAdapter onCurrentListChanged")
//        super.onCurrentListChanged(previousList, currentList)
//    }
//
//    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
//        getItem(position).let { match ->
//            holder.homeName.text = match.homeName
//            holder.awayName.text = match.awayName
//            if (match.isTopOfList) holder.leagueName.text = "置頂" else holder.leagueName.text = "不置頂"
//            holder.imgTop.setOnClickListener { listener?.onSetTopClick(match) }
//        }
//    }
//
////    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
////    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
////        val viewDataBinding = (holder as ViewHolderUserItem).binding
////        getItem(position).let { match ->
////            viewDataBinding.setVariable(BR.homeTeamName, match.homeName)
////            viewDataBinding.setVariable(BR.awayTeamName, match.awayName)
////            if(match.isTopOfList) {
////                viewDataBinding.setVariable(BR.leagueName, "置頂")
////            } else {
////                viewDataBinding.setVariable(BR.leagueName, "無置頂")
////            }
////            viewDataBinding.setVariable(
////                BR.isTopListImg,
////                if (match.isTopOfList) R.drawable.ic_baseline_keyboard_arrow_up else R.drawable.ic_baseline_keyboard_arrow_down
////            )
////            viewDataBinding.root.img_top.setOnClickListener {
////                listener?.onSetTopClick(match)
////            }
////            viewDataBinding.root.layout_match_item.setOnClickListener{
////                listener?.onClickItem(match)
////            }
////        }
////        viewDataBinding.executePendingBindings()
////    }
//
////    override fun submitList(list: List<MatchList.Match?>?) {
////        super.submitList(list?.let { ArrayList(it) })
////    }
//
////    private class ViewHolderUserItem(val binding: ViewDataBinding) :
////        RecyclerView.ViewHolder(binding.root) {
////    }
//
//    class MatchViewHolder(root: View) : RecyclerView.ViewHolder(root) {
//        var homeName: TextView = root.tv_home_team_name
//        var awayName: TextView = root.tv_away_team_name
//        var leagueName: TextView = root.tv_league_name
//        var imgTop: ImageView = root.img_top
//    }
//
//}