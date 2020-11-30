package com.example.mvvm_navigation.ui.main.vm.second

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData

class SecondContract {
    interface ModelImpl{
        suspend fun getUser(): HttpResult<List<UserData.User>>
    }

    interface ViewModelImpl: BaseViewModelImpl<SecondFragmentSubmitter> {

    }
}