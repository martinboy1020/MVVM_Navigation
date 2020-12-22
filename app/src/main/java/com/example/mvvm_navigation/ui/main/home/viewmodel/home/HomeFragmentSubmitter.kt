package com.example.mvvm_navigation.ui.main.home.viewmodel.home

import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.GoalAndLostData
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.ui.main.home.MatchesRecentAdapter
import com.example.mvvm_navigation.ui.main.matchlist.MatchListAdapter
import com.example.mvvm_navigation.widget.BannerWidget
import com.example.mvvm_navigation.widget.GoalAndLostDataWidget

class HomeFragmentSubmitter {
    // UI Data
    val number = MutableLiveData<Int>().default(1)
    val buttonVisible = MutableLiveData<Int>().default(View.VISIBLE)
    val navHostFragment = MutableLiveData<Fragment>()
    val goalData = MutableLiveData<GoalAndLostData>().default(GoalAndLostData(GoalAndLostDataWidget.Type.GOAL, 0f, 0f))
    val lostData = MutableLiveData<GoalAndLostData>().default(GoalAndLostData(GoalAndLostDataWidget.Type.LOST, 0f, 0f))
    val bannerData = MutableLiveData<MutableList<BannerItem>>().default(mutableListOf())
    val showBannerDots = MutableLiveData<Boolean>().default(true)
    val matchesRecentList = MutableLiveData<List<TgMatchRecent.Recent>>().default(mutableListOf())

    // UI Listener
    val recentMatchTimeKeyBtnClickable = MutableLiveData<Boolean>().default(false)
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val bannerClickListener = MutableLiveData<BannerWidget.BannerClickListener>()
    val matchesRecentClickListener = MutableLiveData<MatchesRecentAdapter.MatchListAdapterItemClickListener>()
    val matchFilterClickListener = MutableLiveData<RadioGroup.OnCheckedChangeListener>()
}