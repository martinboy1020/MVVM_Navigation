package com.example.mvvm_navigation.ui.main.home.viewmodel

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition

class RecentConditionAdapter(
    context: Context,
    resource: Int,
    objects: List<RecentMatchCondition>
) : ArrayAdapter<RecentMatchCondition>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.setPadding(0, view.paddingTop, view.paddingRight, view.paddingBottom)
        val label = super.getView(position, convertView, parent) as TextView
        label.text =  this.getItem(position)?.title
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.setPadding(0, view.paddingTop, view.paddingRight, view.paddingBottom)
        val label = super.getView(position, convertView, parent) as TextView
        label.text =  this.getItem(position)?.title
        return label
    }
}