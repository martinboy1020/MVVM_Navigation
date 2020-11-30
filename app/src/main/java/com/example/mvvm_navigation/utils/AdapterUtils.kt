@file:Suppress("UNCHECKED_CAST")

package com.example.mvvm_navigation.utils

import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.SecondAdapter
import com.example.mvvm_navigation.widget.BuildRecyclerView

object AdapterUtils {

    object Adapters{
        const val SECOND = "second"
    }

    @JvmStatic
    @BindingAdapter("adapter", "data", "listener", requireAll = false)
    fun BuildRecyclerView.Adapters(adapter: String, data: Any?, listener: Any?){
        this.adapter = when(adapter){
            Adapters.SECOND -> SecondAdapter(this.context, data as MutableList<UserData.User>, listener as SecondAdapter.SecondAdapterItemClickListener)
            else -> null
        }
    }

}