package com.example.mvvm_navigation.datacenter.network

import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.network.response.MatchList

class DataCenter {
    var bannerList: MutableList<BannerItem> = mutableListOf()
    var matchTopList: MutableList<MatchList.Match> = mutableListOf()
    var matchAllList: MutableList<MatchList.Match> = mutableListOf()
    var matchList: MutableList<MatchList.Match> = mutableListOf()
    var betList: MutableList<BetData> = mutableListOf()
}