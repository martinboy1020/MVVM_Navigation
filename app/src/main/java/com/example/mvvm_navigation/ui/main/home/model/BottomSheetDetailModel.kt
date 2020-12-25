package com.example.mvvm_navigation.ui.main.home.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailContract

class BottomSheetDetailModel constructor(val repository: Repository): BaseModel(), BottomSheetDetailContract.ModelImpl{
    companion object{
        fun getInstance(repository: Repository) =
            BottomSheetDetailModel(
                repository
            )
    }

    override fun getBetList(): List<BetData> {
        return repository.getBetList()
    }

    override suspend fun getMatchStatistics(
        leagueId: Int?,
        teamId1: Int?,
        teamId2: Int?,
        position: Int,
        condition: String
    ): HttpResult<HttpStatus<MatchesStatistics.Data>> {
        return repository.getMatchStatistics(leagueId, teamId1, teamId2, position, condition)
    }

    override fun getRecentMatchCondition(): List<RecentMatchCondition> {
        return repository.getRecentMatchCondition()
    }
}