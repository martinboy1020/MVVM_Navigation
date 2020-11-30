package com.example.mvvm_navigation.ui.main.vm.main

import android.opengl.Visibility
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.GoalAndLostData
import com.example.mvvm_navigation.widget.GoalAndLostDataWidget

class MainFragmentSubmitter {
//    val tutorialList = MutableLiveData<MutableList<Int>>().default(Config.tutorials)
//    val tutorialTitleList = MutableLiveData<MutableList<Int>>().default(Config.tutorialTitles)
    val number = MutableLiveData<Int>().default(1)
    val buttonVisible = MutableLiveData<Int>().default(View.GONE)
    val navHostFragment = MutableLiveData<Fragment>()
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val goalData = MutableLiveData<GoalAndLostData>().default(GoalAndLostData(GoalAndLostDataWidget.Type.GOAL, 0f, 0f))
    val lostData = MutableLiveData<GoalAndLostData>().default(GoalAndLostData(GoalAndLostDataWidget.Type.LOST, 0f, 0f))
}