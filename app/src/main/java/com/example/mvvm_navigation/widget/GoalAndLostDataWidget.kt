package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.example.mvvm_navigation.R

class GoalAndLostDataWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null

    init {
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_goal_lost_data, this)
    }

}