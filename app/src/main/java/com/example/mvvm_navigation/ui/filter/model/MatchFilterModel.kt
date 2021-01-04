package com.example.mvvm_navigation.ui.filter.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.ui.filter.viewmodel.match_filter.MatchFilterContract

class MatchFilterModel constructor(val repository: Repository): BaseModel(), MatchFilterContract.ModelImpl{
    companion object{
        fun getInstance(repository: Repository) =
            MatchFilterModel(repository)
    }
}