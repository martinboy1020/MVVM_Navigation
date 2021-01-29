package com.example.mvvm_navigation.widget.match_filter_widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.widget.ImageShapeWidget
import kotlinx.android.synthetic.main.layout_item_cb_match_selector.view.*

class MatchFilterAdapter(
    typeData: Int,
    listener: MatchFilterAdapterOnChangeListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var type: Int? = 0
    private var areaList: MutableList<MatchList.Area> = mutableListOf()
    private var countryList: MutableList<MatchList.Country> = mutableListOf()
    private var leagueList: MutableList<MatchList.Leagues> = mutableListOf()
    private var matchFilterOnChangeListener: MatchFilterAdapterOnChangeListener? = null
    private var isSelectedAll: Boolean = false

    init {
        type = typeData
        matchFilterOnChangeListener = listener
    }

    interface MatchFilterAdapterOnChangeListener {
        fun changeStatus(id: Int, name: String, isCheck: Boolean)
    }

    companion object {
        const val TYPE_AREA = 0
        const val TYPE_COUNTRY = 1
        const val TYPE_LEAGUE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterItemViewHolder(
            inflater.inflate(
                R.layout.layout_item_cb_match_selector,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return when (type) {
            TYPE_AREA -> {
                areaList.size
            }
            TYPE_COUNTRY -> {
                countryList.size
            }
            TYPE_LEAGUE -> {
                leagueList.size
            }
            else -> {
                0
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as FilterItemViewHolder
        itemHolder.img.visibility = View.GONE
        when (type) {
            TYPE_AREA -> {
                itemHolder.itemName.text = areaList[position].name
            }
            TYPE_COUNTRY -> {
                itemHolder.itemName.text = countryList[position].name
            }
            TYPE_LEAGUE -> {
                itemHolder.itemName.text = leagueList[position].name
            }
        }
        itemHolder.cb.isChecked = isSelectedAll
        itemHolder.cb.setOnClickListener {
            val isChecked = holder.cb.isChecked
            holder.cb.isChecked = isChecked
            when (type) {
                TYPE_AREA -> {
                    areaList[position].isCheck = isChecked
                    matchFilterOnChangeListener?.changeStatus(areaList[holder.getAdapterPosition()].id, areaList[holder.getAdapterPosition()].name, isChecked)
                }
                TYPE_COUNTRY -> {
                    countryList[position].isCheck = isChecked
                    matchFilterOnChangeListener?.changeStatus(countryList[position].id, countryList[holder.getAdapterPosition()].name, isChecked)
                }
                TYPE_LEAGUE -> {
                    leagueList[position].isCheck = isChecked
                    matchFilterOnChangeListener?.changeStatus(leagueList[position].id, leagueList[holder.getAdapterPosition()].name, isChecked)
                }
            }
        }
    }

    fun setAreaList(areaList: MutableList<MatchList.Area>) {
        this.areaList = areaList
        notifyDataSetChanged()
    }

    fun setCountryList(countryList: MutableList<MatchList.Country>) {
        this.countryList = countryList
        notifyDataSetChanged()
    }

    fun setLeagueList(leagueList: MutableList<MatchList.Leagues>) {
        this.leagueList = leagueList
        notifyDataSetChanged()
    }

    private class FilterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cb: CheckBox = itemView.cb_item_match_selector
        var itemName: TextView = itemView.tv_item_match_selector
        var img: ImageShapeWidget = itemView.img_item_match_selector
    }

    fun allSelected() {
        isSelectedAll = true
        notifyDataSetChanged()
    }

    fun allUnselected() {
        isSelectedAll = false
        notifyDataSetChanged()
    }

    fun getAreaList(): MutableList<MatchList.Area> {
        return areaList
    }

    fun getCountryList(): MutableList<MatchList.Country> {
        return countryList
    }

    fun getLeagueList(): MutableList<MatchList.Leagues> {
        return leagueList
    }

}