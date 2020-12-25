package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import android.view.View
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
    //    val tutorialList = MutableLiveData<MutableList<Int>>().default(Config.tutorials)
//    val tutorialTitleList = MutableLiveData<MutableList<Int>>().default(Config.tutorialTitles)
    val number = MutableLiveData<Int>().default(1)
    val buttonVisible = MutableLiveData<Int>().default(View.GONE)
    val navHostFragment = MutableLiveData<Fragment>()
    val leagueTeamData = MutableLiveData<LeagueTeamData>()
    val matchStatisticsValue = MutableLiveData<BottomSheetDetailViewModel.MatchStatisticsValue>()
    val goalData = MutableLiveData<MatchesStatistics.GoalAndLostData>()
    val lostData = MutableLiveData<MatchesStatistics.GoalAndLostData>()
    val betList = MutableLiveData<List<BetData>>().default(mutableListOf())
    val recentMatchConditionList =
        MutableLiveData<List<RecentMatchCondition>>().default(mutableListOf())

    val onClickListener = MutableLiveData<View.OnClickListener>()
    val checkBoxCheckedListener = MutableLiveData<ItemMatchSelectorWidget.CheckBoxListener>()
    val homeAwayFilterListener = MutableLiveData<RadioGroup.OnCheckedChangeListener>()
}