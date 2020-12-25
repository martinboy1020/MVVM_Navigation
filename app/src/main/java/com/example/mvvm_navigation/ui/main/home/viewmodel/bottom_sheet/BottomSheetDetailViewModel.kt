package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.data.LeagueTeamData
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.GoalOrLostType
import com.example.mvvm_navigation.widget.ItemMatchSelectorWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.FieldPosition

class BottomSheetDetailViewModel constructor(
    application: Application,
    context: Context,
    private val listener: BottomSheetDetailViewModelImpl,
    val model: BottomSheetDetailContract.ModelImpl,
    navController: NavController
) : BaseViewModel(application, context, navController), BottomSheetDetailContract.ViewModelImpl,
    View.OnClickListener, ItemMatchSelectorWidget.CheckBoxListener,
    RadioGroup.OnCheckedChangeListener {

    private val submitter =
        BottomSheetDetailFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
        this.submitter.betList.value = model.getBetList()
        this.submitter.recentMatchConditionList.value = model.getRecentMatchCondition()
        this.submitter.homeAwayFilterListener.value = this
        this.submitter.checkBoxCheckedListener.value = this
    }

    data class MatchStatisticsValue(
        var leagueId: Int? = null,
        var homeId: Int? = null,
        var awayId: Int? = null,
        var position: Int = 0,
        var condition: String = "hundredMatches"
    )

    interface BottomSheetDetailViewModelImpl {
        fun closeDialog()
    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            listener: BottomSheetDetailViewModelImpl,
            model: BottomSheetDetailContract.ModelImpl,
            navController: NavController
        ) =
            BottomSheetDetailViewModel(
                application,
                context,
                listener,
                model,
                navController
            )
    }

    class Factory(
        val application: Application,
        val context: Context,
        val listener: BottomSheetDetailViewModelImpl,
        val model: BottomSheetDetailContract.ModelImpl,
        val navController: NavController
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            listener,
            model,
            navController
        ) as T
    }

    override fun getMatchStatistics(
        matchStatisticsValue: MatchStatisticsValue
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val matchStatistics =
                model.getMatchStatistics(
                    matchStatisticsValue.leagueId,
                    matchStatisticsValue.homeId,
                    matchStatisticsValue.awayId,
                    matchStatisticsValue.position,
                    matchStatisticsValue.condition
                )
            withContext(Dispatchers.Main) {
                when (matchStatistics) {
                    is HttpResult.onSuccess -> {
                        val data = matchStatistics.data.payload
                        val goalData = data.goalData
                        goalData.type = GoalOrLostType.GOAL
                        val lostData = data.lostData
                        lostData.type = GoalOrLostType.LOST
                        this@BottomSheetDetailViewModel.submitter.goalData.value = data.goalData
                        this@BottomSheetDetailViewModel.submitter.lostData.value = data.lostData
                    }
                    is HttpResult.onError -> {

                    }
                }
            }
        }
    }

    override fun getSubmitter(): BottomSheetDetailFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_close -> {
                listener.closeDialog()
            }
        }
    }

    override fun getFilterType(type: Int, isChecked: Boolean) {
        val matchStatisticsValue = this.getSubmitter().matchStatisticsValue.value
        when (type) {
            ItemMatchSelectorWidget.FilterType.LEAGUE.type -> {
                matchStatisticsValue?.leagueId =
                    if (isChecked) this.getSubmitter().leagueTeamData.value?.leagueId else null
            }
            ItemMatchSelectorWidget.FilterType.HOME.type -> {
                matchStatisticsValue?.homeId =
                    if (isChecked) this.getSubmitter().leagueTeamData.value?.homeId else null
            }
            ItemMatchSelectorWidget.FilterType.AWAY.type -> {
                matchStatisticsValue?.awayId =
                    if (isChecked) this.getSubmitter().leagueTeamData.value?.awayId else null
            }
        }
        this.getSubmitter().matchStatisticsValue.value = matchStatisticsValue
        if (matchStatisticsValue != null) getMatchStatistics(matchStatisticsValue)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val radBtn = p0?.findViewById<RadioButton>(p1)
        Toast.makeText(this.context, "Filter is ${radBtn?.text}", Toast.LENGTH_SHORT).show()
    }
}