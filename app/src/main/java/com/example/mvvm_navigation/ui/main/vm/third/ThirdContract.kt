package com.example.mvvm_navigation.ui.main.vm.third

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.MatchListItem

class ThirdContract {
    interface ModelImpl{
        fun getMatchList(): MutableList<MatchListItem>
        fun setMatchItemToTopList(data: MatchListItem): MutableList<MatchListItem>
    }

    interface ViewModelImpl: BaseViewModelImpl<ThirdFragmentSubmitter> {

    }
}