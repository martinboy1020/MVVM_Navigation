package com.example.mvvm_navigation.ui.filter.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter.IncorrectScoreFilterContract

class IncorrectScoreFilterModel constructor(val repository: Repository) : BaseModel(),
    IncorrectScoreFilterContract.ModelImpl {
    companion object {
        fun getInstance(repository: Repository) =
            IncorrectScoreFilterModel(repository)
    }
}