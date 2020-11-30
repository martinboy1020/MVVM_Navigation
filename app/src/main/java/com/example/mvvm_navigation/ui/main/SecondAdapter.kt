package com.example.mvvm_navigation.ui.main

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.widget.UserItemWidget
import kotlinx.android.synthetic.main.rv_item_user.view.*

class SecondAdapter(
    val context: Context,
    val data: List<UserData.User>,
    private val listener: SecondAdapterItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface SecondAdapterItemClickListener {
        fun onItemClick(view: View, data: UserData.User, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolderUserItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_item_user,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewDataBinding = (holder as ViewHolderUserItem).binding
        viewDataBinding.setVariable(
            BR.user_name,
            StringBuilder().append("UserName: ").append(data[position].username).toString()
        )
        viewDataBinding.setVariable(
            BR.real_name,
            StringBuilder().append("RealName: ").append(data[position].name).toString()
        )
        viewDataBinding.setVariable(
            BR.email,
            StringBuilder().append("Email: ").append(data[position].email).toString()
        )
//        viewDataBinding.executePendingBindings()
        val view = viewDataBinding.root.user_item_widget
        view.setBackgroundColor(ContextCompat.getColor(this.context, android.R.color.white))
        ViewCompat.setTransitionName(view, "test$position")
        viewDataBinding.root.setOnClickListener { listener.onItemClick(view, data[position], position) }
    }

    private class ViewHolderUserItem(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}