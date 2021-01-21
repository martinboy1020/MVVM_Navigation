package com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.IncorrectScoreData

class IncorrectScoreFilterContract {
    interface ModelImpl {
        fun getIncorrectDataList(): MutableList<IncorrectScoreData>?
    }

    interface ViewModelImpl : BaseViewModelImpl<IncorrectScoreFilterFragmentSubmitter> {

    }

}