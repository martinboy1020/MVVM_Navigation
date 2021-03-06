package com.example.mvvm_navigation.ui.main.home.viewmodel.home

import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.ui.main.home.MatchesRecentAdapter
import com.example.mvvm_navigation.widget.BannerWidget

class HomeFragmentSubmitter {
    // UI Data
    val navHostFragment = MutableLiveData<Fragment>()
    val bannerData = MutableLiveData<MutableList<BannerItem>>().default(mutableListOf())
    val showBannerDots = MutableLiveData<Boolean>().default(true)
    val matchesRecentList = MutableLiveData<List<TgMatchRecent.Recent>>().default(mutableListOf())

    // UI Listener
    val recentMatchTimeKeyBtnClickable = MutableLiveData<Boolean>().default(false)
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val bannerClickListener = MutableLiveData<BannerWidget.BannerClickListener>()
    val topListBtnClickListener = MutableLiveData<CompoundButton.OnCheckedChangeListener>()
    val matchesRecentClickListener = MutableLiveData<MatchesRecentAdapter.MatchListAdapterItemClickListener>()
    val matchFilterClickListener = MutableLiveData<RadioGroup.OnCheckedChangeListener>()
}