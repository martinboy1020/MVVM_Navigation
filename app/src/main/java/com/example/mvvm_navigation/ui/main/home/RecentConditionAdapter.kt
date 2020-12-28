package com.example.mvvm_navigation.ui.main.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics

class RecentConditionAdapter(
    context: Context,
    resource: Int,
    objects: List<MatchesStatistics.Season>
) : ArrayAdapter<MatchesStatistics.Season>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.setPadding(0, view.paddingTop, view.paddingRight, view.paddingBottom)
        val label = super.getView(position, convertView, parent) as TextView
        label.text =  this.getItem(position)?.name
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.setPadding(0, view.paddingTop, view.paddingRight, view.paddingBottom)
        val label = super.getView(position, convertView, parent) as TextView
        label.text =  this.getItem(position)?.name
        return label
    }
}