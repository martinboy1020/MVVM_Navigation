package com.example.mvvm_navigation.datacenter.network

import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.MatchListItem

class DataCenter {
    var bannerList: MutableList<BannerItem> = mutableListOf()
    var matchTopList: MutableList<MatchListItem> = mutableListOf()
    var matchList: MutableList<MatchListItem> = mutableListOf()
    var matchAllList: MutableList<MatchListItem> = mutableListOf()
}