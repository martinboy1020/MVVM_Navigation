package com.example.mvvm_navigation.ui.main.vm.main

import android.opengl.Visibility
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.GoalAndLostData
import com.example.mvvm_navigation.widget.BannerWidget
import com.example.mvvm_navigation.widget.GoalAndLostDataWidget

class MainFragmentSubmitter {
    // UI Data
    val number = MutableLiveData<Int>().default(1)
    val buttonVisible = MutableLiveData<Int>().default(View.VISIBLE)
    val navHostFragment = MutableLiveData<Fragment>()
    val goalData = MutableLiveData<GoalAndLostData>().default(GoalAndLostData(GoalAndLostDataWidget.Type.GOAL, 0f, 0f))
    val lostData = MutableLiveData<GoalAndLostData>().default(GoalAndLostData(GoalAndLostDataWidget.Type.LOST, 0f, 0f))
    val bannerData = MutableLiveData<MutableList<BannerItem>>().default(mutableListOf())
    val showBannerDots = MutableLiveData<Boolean>().default(true)

    // UI Listener
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val bannerClickListener = MutableLiveData<BannerWidget.BannerClickListener>()
}