package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.GoalAndLostData

@BindingAdapter("data", requireAll = false)
fun setGoalAndLostData(view: GoalAndLostDataWidget, data: GoalAndLostData) {
    view.setData(data)
}

class GoalAndLostDataWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null

    enum class Type { GOAL, LOST }

    init {
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_goal_lost_data, this)
    }

    fun setData(data: GoalAndLostData) {

        val tvTitle = view?.findViewById<TextView>(R.id.tv_title)
        val tvAvg = view?.findViewById<TextView>(R.id.tv_avg)

        when(data.type) {
            Type.GOAL -> {
                tvTitle?.text = "進球"
                tvAvg?.text = "平均進球: ${data.avgGoal}"
            }
            Type.LOST -> {
                tvTitle?.text = "失球"
                tvAvg?.text = "平均失球: ${data.avgLost}"
            }
        }
    }

}