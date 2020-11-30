package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R

@BindingAdapter("name", "realName", "email", requireAll = false)
fun setUserItemWidgetData(view: UserItemWidget, name: String, realName: String, email: String) {
    view.setData(name, realName, email)
}

class UserItemWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null

    init {
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.item_user, this)
    }

    fun setData(name: String, realName: String, email: String) {
        val tvUserName = view?.findViewById<TextView>(R.id.user_name)
        val tvRealName = view?.findViewById<TextView>(R.id.real_name)
        val tvEmail = view?.findViewById<TextView>(R.id.email)
        tvUserName?.text = name
        tvRealName?.text = realName
        tvEmail?.text = email
    }

}