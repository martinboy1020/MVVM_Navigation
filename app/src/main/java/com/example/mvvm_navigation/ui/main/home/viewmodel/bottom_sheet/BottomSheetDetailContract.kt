package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition

class BottomSheetDetailContract {
    interface ModelImpl {
        fun getBetList(): List<BetData>
        fun getRecentMatchCondition(): List<RecentMatchCondition>
    }

    interface ViewModelImpl : BaseViewModelImpl<BottomSheetDetailFragmentSubmitter> {

    }

}