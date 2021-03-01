package com.example.mvvm_navigation.ui.main.home.viewmodel.recent_match

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.*

class RecentMatchContract {
    interface ModelImpl {
        suspend fun getTgMatchesRecent(timeKey: String): HttpResult<HttpStatus<MutableList<TgMatchRecent.Recent>>>
    }

    interface ViewModelImpl : BaseViewModelImpl<RecentMatchFragmentSubmitter> {

    }
}