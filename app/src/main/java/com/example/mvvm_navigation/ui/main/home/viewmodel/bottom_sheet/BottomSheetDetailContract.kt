package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.LeagueTeamData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics

class BottomSheetDetailContract {
    interface ModelImpl {
        fun getBetList(): List<BetData>
        suspend fun getMatchStatistics(
            leagueId: Int?,
            teamId1: Int?,
            teamId2: Int?,
            position: Int = 0,
            condition: String = ""
        ): HttpResult<HttpStatus<MatchesStatistics.Data>>
        fun getRecentMatchCondition(): List<RecentMatchCondition>
    }

    interface ViewModelImpl : BaseViewModelImpl<BottomSheetDetailFragmentSubmitter> {
        fun getMatchStatistics(matchStatisticsValue: BottomSheetDetailViewModel.MatchStatisticsValue)
    }

}