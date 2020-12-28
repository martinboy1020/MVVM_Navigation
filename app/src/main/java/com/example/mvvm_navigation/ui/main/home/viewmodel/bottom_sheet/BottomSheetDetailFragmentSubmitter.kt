package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import android.util.MutableDouble
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.LeagueTeamData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics
import com.example.mvvm_navigation.widget.ItemMatchSelectorWidget

class BottomSheetDetailFragmentSubmitter {
    // Data
    val leagueTeamData = MutableLiveData<LeagueTeamData>()
    val matchStatisticsValue = MutableLiveData<BottomSheetDetailViewModel.MatchStatisticsValue>()
    val goalData = MutableLiveData<MatchesStatistics.GoalAndLostData>()
    val lostData = MutableLiveData<MatchesStatistics.GoalAndLostData>()
    val betList = MutableLiveData<List<BetData>>().default(mutableListOf())
    val recentMatchConditionList =
        MutableLiveData<List<MatchesStatistics.Season>>().default(mutableListOf())
    val dataDescription = MutableLiveData<String>().default("目前的資料描述:")

    // UI Listener
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val checkBoxCheckedListener = MutableLiveData<ItemMatchSelectorWidget.CheckBoxListener>()
    val homeAwayFilterListener = MutableLiveData<RadioGroup.OnCheckedChangeListener>()
    val switchListener = MutableLiveData<CompoundButton.OnCheckedChangeListener>()
    val spinnerSelectedListener = MutableLiveData<AdapterView.OnItemSelectedListener>()

    // UI Status
    val radioBtnEnable = MutableLiveData<Boolean>().default(true)
}