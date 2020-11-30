package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.mvvm_navigation.R

class ItemMatchSelectorWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null

    init {
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_item_cb_match_selector, this)
    }

}