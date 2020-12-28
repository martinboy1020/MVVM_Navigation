package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.GoalOrLostType
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics
import com.example.mvvm_navigation.widget.ItemMatchSelectorWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class BottomSheetDetailViewModel constructor(
    application: Application,
    context: Context,
    private val listener: BottomSheetDetailViewModelImpl,
    val model: BottomSheetDetailContract.ModelImpl,
    navController: NavController
) : BaseViewModel(application, context, navController), BottomSheetDetailContract.ViewModelImpl,
    View.OnClickListener, ItemMatchSelectorWidget.CheckBoxListener,
    RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener,
    AdapterView.OnItemSelectedListener {

    private val submitter =
        BottomSheetDetailFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
        this.submitter.betList.value = model.getBetList()
        this.submitter.homeAwayFilterListener.value = this
        this.submitter.checkBoxCheckedListener.value = this
        this.submitter.switchListener.value = this
        this.submitter.spinnerSelectedListener.value = this
    }

    data class MatchStatisticsValue(
        var leagueId: Int? = null,
        var homeId: Int? = null,
        var awayId: Int? = null,
        var position: Int = 1,
        var condition: String = "thirtyMatches"
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
        changeDataDescription(matchStatisticsValue)
        CoroutineScope(Dispatchers.IO).launch {
            val matchStatistics =
                model.getMatchStatistics(
                    matchStatisticsValue.leagueId,
                    matchStatisticsValue.homeId,
                    matchStatisticsValue.awayId,
                    if ((matchStatisticsValue.homeId != null
                                && matchStatisticsValue.awayId != null) || matchStatisticsValue.homeId == null
                        && matchStatisticsValue.awayId == null
                    ) 0 else matchStatisticsValue.position,
                    matchStatisticsValue.condition
                )
            withContext(Dispatchers.Main) {
                when (matchStatistics) {
                    is HttpResult.onSuccess -> {
                        val data = matchStatistics.data.payload
                        val goalData = data.goalData
                        goalData.type = GoalOrLostType.GOAL
                        goalData.totalCount = data.totalCount
                        val lostData = data.lostData
                        lostData.type = GoalOrLostType.LOST
                        lostData.totalCount = data.totalCount
                        this@BottomSheetDetailViewModel.submitter.goalData.value = data.goalData
                        this@BottomSheetDetailViewModel.submitter.lostData.value = data.lostData

                        if (getSubmitter().recentMatchConditionList.value.isNullOrEmpty()) {
                            val seasons = data.seasons
                            getSubmitter().recentMatchConditionList.value = seasons
                        }

                    }
                    is HttpResult.onError -> {

                    }
                }
            }
        }
    }

    // 目前所選擇的資料描述文字
    private fun changeDataDescription(matchStatisticsValue: MatchStatisticsValue) {
        val leagueTeamData = getSubmitter().leagueTeamData.value
        val recentMatchConditionList = getSubmitter().recentMatchConditionList.value
        val stringBuilder = StringBuilder().append("目前的資料描述: ")
        if (matchStatisticsValue.homeId != null && matchStatisticsValue.awayId != null) {
            stringBuilder.append(
                String.format(
                    ("%s vs %s"),
                    leagueTeamData?.homeName,
                    leagueTeamData?.awayName
                )
            )
            if (matchStatisticsValue.leagueId != null) stringBuilder.append(" 在 ")
                .append(leagueTeamData?.leagueName)
        } else if (matchStatisticsValue.homeId != null || matchStatisticsValue.awayId != null) {
            if (matchStatisticsValue.homeId != null) stringBuilder.append(leagueTeamData?.homeName)
            if (matchStatisticsValue.awayId != null) stringBuilder.append(leagueTeamData?.awayName)
            if (matchStatisticsValue.leagueId != null) stringBuilder.append(" 在 ")
                .append(leagueTeamData?.leagueName)
            when (matchStatisticsValue.position) {
                0 -> {
                    stringBuilder.append(" 不分主客場 ")
                }
                1 -> {
                    stringBuilder.append(" 主場 ")
                }
                2 -> {
                    stringBuilder.append(" 客場 ")
                }
            }
        } else {
            if (matchStatisticsValue.leagueId != null) stringBuilder.append(leagueTeamData?.leagueName)
        }
        if (recentMatchConditionList != null) {
            for (i in recentMatchConditionList.indices) {
                if (matchStatisticsValue.condition == recentMatchConditionList[i].id) {
                    stringBuilder.append(" " + recentMatchConditionList[i].name)
                    break
                }
            }
        } else {
            stringBuilder.append(" 近30場")
        }
        getSubmitter().dataDescription.value = stringBuilder.toString()
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

        if (matchStatisticsValue != null) {
            this.getSubmitter().radioBtnEnable.value =
                !((matchStatisticsValue.homeId != null && matchStatisticsValue.awayId != null) ||
                        (matchStatisticsValue.homeId == null && matchStatisticsValue.awayId == null))
        }

        this.getSubmitter().matchStatisticsValue.value = matchStatisticsValue
        if (matchStatisticsValue != null) getMatchStatistics(matchStatisticsValue)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val rtb = p0?.findViewById<RadioButton>(p1)
        val matchStatisticsValue = this.getSubmitter().matchStatisticsValue.value
        when (p1) {
            R.id.rtb_home -> {
                matchStatisticsValue?.position = 1
            }
            R.id.rtb_away -> {
                matchStatisticsValue?.position = 2
            }
            R.id.rtb_all -> {
                matchStatisticsValue?.position = 0
            }
        }
        this.getSubmitter().matchStatisticsValue.value = matchStatisticsValue
        if (matchStatisticsValue != null) getMatchStatistics(matchStatisticsValue)
    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        val goalData = this.submitter.goalData.value
        val lostData = this.submitter.lostData.value
        when (isChecked) {
            true -> {
                goalData?.isDisappearStatus = true
                lostData?.isDisappearStatus = true
            }
            false -> {
                goalData?.isDisappearStatus = false
                lostData?.isDisappearStatus = false
            }
        }
        this.submitter.goalData.value = goalData
        this.submitter.lostData.value = lostData
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item: Any? = p0?.getItemAtPosition(p2)
        if (item is MatchesStatistics.Season) {
            val matchStatisticsValue = getSubmitter().matchStatisticsValue.value
            if (matchStatisticsValue?.condition != item.id) {
                matchStatisticsValue?.condition = item.id
                this.getSubmitter().matchStatisticsValue.value = matchStatisticsValue
                if (matchStatisticsValue != null) getMatchStatistics(matchStatisticsValue)
            }
        }
    }
}