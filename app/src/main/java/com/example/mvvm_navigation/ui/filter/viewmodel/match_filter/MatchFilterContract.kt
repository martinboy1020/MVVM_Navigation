package com.example.mvvm_navigation.ui.filter.viewmodel.match_filter

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter.IncorrectScoreFilterFragmentSubmitter

class MatchFilterContract {
    interface ModelImpl {
        fun getFilterAreaList(): MutableList<MatchList.Area>
        fun getFilterCountryList(): MutableList<MatchList.Country>
        fun getFilterLeagueList(): MutableList<MatchList.Leagues>
    }

    interface ViewModelImpl : BaseViewModelImpl<MatchFilterFragmentSubmitter> {
        fun setMatchFilter(mutableList: MutableList<MatchList.Area>)
    }

}