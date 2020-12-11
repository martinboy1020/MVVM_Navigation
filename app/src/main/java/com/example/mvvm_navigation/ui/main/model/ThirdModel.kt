package com.example.mvvm_navigation.ui.main.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract
import com.example.mvvm_navigation.ui.main.vm.third.ThirdContract

class ThirdModel constructor(val repository: Repository) : BaseModel(), ThirdContract.ModelImpl {
    companion object {
        fun getInstance(repository: Repository) = ThirdModel(repository)
    }

    override fun getMatchList(): MutableList<MatchListItem> {
        return this@ThirdModel.repository.getMatchList()
    }

    override fun setMatchItemToTopList(data: MatchListItem): MutableList<MatchListItem> {
        return this@ThirdModel.repository.refreshTopListMatch(data)
    }

}