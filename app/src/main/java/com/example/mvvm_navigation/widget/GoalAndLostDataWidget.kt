package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.GoalOrLostType
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics
import kotlinx.android.synthetic.main.layout_goal_lost_data.view.*

@BindingAdapter("data", requireAll = false)
fun setGoalAndLostData(view: GoalAndLostDataWidget, data: MatchesStatistics.GoalAndLostData?) {
    Log.d("tag12345", "setGoalAndLostData: $data")
    if(data != null) view.setData(data)
}

class GoalAndLostDataWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null
    private var binding: ViewDataBinding? = null

    init {
        initView()
    }

    private fun initView() {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_goal_lost_data,
            this,
            true
        )
        view = binding?.root
    }

    fun setData(data: MatchesStatistics.GoalAndLostData) {
        Log.d("tag12345", "GoalAndLostDataWidget data: $data")
        when (data.type) {
            GoalOrLostType.GOAL -> {
                view?.tv_title?.text = "進球"
                view?.tv_avg?.text = "平均進球: ${data.avgGoal}"
            }
            GoalOrLostType.LOST -> {
                view?.tv_title?.text = "失球"
                view?.tv_avg?.text = "平均失球: ${data.avgLost}"
            }

        }
    }

}