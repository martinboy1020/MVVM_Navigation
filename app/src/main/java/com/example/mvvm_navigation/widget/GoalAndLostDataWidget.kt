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
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.GoalOrLostType
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics
import kotlinx.android.synthetic.main.layout_goal_lost_data.view.*

@BindingAdapter("data", requireAll = false)
fun setGoalAndLostData(view: GoalAndLostDataWidget, data: MatchesStatistics.GoalAndLostData?) {
    if (data != null) view.setData(data)
}

class GoalAndLostDataWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null
    private var binding: ViewDataBinding? = null
    private var oneBallData: GoalAndLostWidgetData = GoalAndLostWidgetData()
    private var twoBallData: GoalAndLostWidgetData = GoalAndLostWidgetData()
    private var threeBallData: GoalAndLostWidgetData = GoalAndLostWidgetData()
    private var overFourBallData: GoalAndLostWidgetData = GoalAndLostWidgetData()

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
        when (data.isDisappearStatus) {
            true -> {
                oneBallData.titleString =
                    "未出現率${data.one.notAppearRate}(${data.one.notAppearCnt}場/${data.totalCount}場)"
                oneBallData.descriptionString = getContinueCountAndSeasonDescription(data.one, true)
                twoBallData.titleString =
                    "未出現率${data.two.notAppearRate}(${data.two.notAppearCnt}場/${data.totalCount}場)"
                twoBallData.descriptionString = getContinueCountAndSeasonDescription(data.two, true)
                threeBallData.titleString =
                    "未出現率${data.three.notAppearRate}(${data.three.notAppearCnt}場/${data.totalCount}場)"
                threeBallData.descriptionString = getContinueCountAndSeasonDescription(data.three, true)
                overFourBallData.titleString =
                    "未出現率${data.fourOrMore.notAppearRate}(${data.fourOrMore.notAppearCnt}場/${data.totalCount}場)"
                overFourBallData.descriptionString = getContinueCountAndSeasonDescription(data.fourOrMore, true)
            }
            false -> {
                oneBallData.titleString =
                    "出現率${data.one.appearRate}(${data.one.notAppearCnt}場/${data.totalCount}場)"
                oneBallData.descriptionString = getContinueCountAndSeasonDescription(data.one, false)
                twoBallData.titleString =
                    "出現率${data.two.appearRate}(${data.two.notAppearCnt}場/${data.totalCount}場)"
                twoBallData.descriptionString = getContinueCountAndSeasonDescription(data.two, false)
                threeBallData.titleString =
                    "出現率${data.three.appearRate}(${data.three.notAppearCnt}場/${data.totalCount}場)"
                threeBallData.descriptionString = getContinueCountAndSeasonDescription(data.three, false)
                overFourBallData.titleString =
                    "出現率${data.fourOrMore.appearRate}(${data.fourOrMore.notAppearCnt}場/${data.totalCount}場)"
                overFourBallData.descriptionString = getContinueCountAndSeasonDescription(data.fourOrMore, false)
            }
        }
        binding?.setVariable(BR.oneBall, oneBallData)
        binding?.setVariable(BR.twoBall, twoBallData)
        binding?.setVariable(BR.threeBall, threeBallData)
        binding?.setVariable(BR.overFourBall, overFourBallData)
    }

    private fun getContinueCountAndSeasonDescription(
        data: MatchesStatistics.StaticDetailData,
        isDisappearStatus: Boolean
    ): String {
        val stringBuilder = StringBuilder()
        if (isDisappearStatus) {
            if (data.notContinuedCount == 0 && data.notContinuedSeasons == 0) {
                stringBuilder.append("-")
            } else {
                if (data.notContinuedCount > 0) {
                    stringBuilder.append(String.format("連%s場", data.notContinuedCount))
                }
                if (data.notContinuedSeasons > 0) {
                    stringBuilder.append(String.format("(跨%s季)", data.notContinuedSeasons))
                }
                stringBuilder.append("未出現")
            }
        } else {
            if (data.continuedCount == 0 && data.continuedSeasons == 0) {
                stringBuilder.append("-")
            } else {
                if (data.continuedCount > 0) {
                    stringBuilder.append(String.format("連%s場", data.continuedCount))
                }
                if (data.continuedSeasons > 0) {
                    stringBuilder.append(String.format("(跨%s季)", data.continuedSeasons))
                }
                stringBuilder.append("出現")
            }
        }
        return stringBuilder.toString()
    }

    data class GoalAndLostWidgetData(
        var titleString: String = "",
        var descriptionString: String = ""
    )

}