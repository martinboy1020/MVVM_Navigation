package com.example.mvvm_navigation.ui.main.vm.bottom_sheet

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData

class BottomSheetDetailContract {
    interface ModelImpl {
        suspend fun getUserData(): HttpResult<List<UserData.User>>
    }

    interface ViewModelImpl : BaseViewModelImpl<BottomSheetDetailFragmentSubmitter> {

    }

}