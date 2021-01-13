package com.example.mvvm_navigation.ui.filter.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.filter.viewmodel.match_filter.MatchFilterContract

class MatchFilterModel constructor(val repository: Repository) : BaseModel(),
    MatchFilterContract.ModelImpl {
    companion object {
        fun getInstance(repository: Repository) =
            MatchFilterModel(repository)
    }

    override fun getFilterAreaList(): MutableList<MatchList.Area> {
        return this.repository.getFilterAreaList()
    }

    override fun getFilterCountryList(): MutableList<MatchList.Country> {
        return this.repository.getFilterCountryList()
    }

    override fun getFilterLeagueList(): MutableList<MatchList.Leagues> {
        return this.repository.getFilterLeagueList()
    }
}