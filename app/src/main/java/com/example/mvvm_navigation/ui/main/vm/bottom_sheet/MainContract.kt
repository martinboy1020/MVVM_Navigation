package com.example.mvvm_navigation.ui.main.vm.bottom_sheet

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData

class BottomSheetDetailContract {
    interface ModelImpl {
        fun getBetList(): List<BetData>
    }

    interface ViewModelImpl : BaseViewModelImpl<BottomSheetDetailFragmentSubmitter> {

    }

}