package com.example.mvvm_navigation.ui.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.IncorrectScoreData
import com.example.mvvm_navigation.widget.ImageShapeWidget
import kotlinx.android.synthetic.main.layout_item_cb_match_selector.view.*

class IncorrectScoreFilterAdapter(
    val data: MutableList<IncorrectScoreData>?,
    listener: IncorrectScoreFilterAdapterOnChangeListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var matchFilterOnChangeListener: IncorrectScoreFilterAdapterOnChangeListener? = null
    private var isSelectedAll: Boolean = false
    private var incorrectDataList: MutableList<IncorrectScoreData> = mutableListOf()

    init {
        if (data != null) incorrectDataList = data
        matchFilterOnChangeListener = listener
    }

    interface IncorrectScoreFilterAdapterOnChangeListener {
        fun changeStatus(id: Int, name: String, isCheck: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IncorrectScoreItemViewHolder(
            inflater.inflate(
                R.layout.layout_item_cb_match_selector,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return incorrectDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as IncorrectScoreItemViewHolder
        itemHolder.img.visibility = View.GONE
        itemHolder.itemName.text = incorrectDataList[position].name
        itemHolder.cb.isChecked = isSelectedAll
        itemHolder.cb.setOnClickListener {
            val isChecked = holder.cb.isChecked
            holder.cb.isChecked = isChecked
        }
    }

    private class IncorrectScoreItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cb: CheckBox = itemView.cb_item_match_selector
        var itemName: TextView = itemView.tv_item_match_selector
        var img: ImageShapeWidget = itemView.img_item_match_selector
    }

}