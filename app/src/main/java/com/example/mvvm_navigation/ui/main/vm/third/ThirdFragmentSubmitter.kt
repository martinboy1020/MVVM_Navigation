package com.example.mvvm_navigation.ui.main.vm.third

import android.view.View
import androidx.lifecycle.MutableLiveData

class ThirdFragmentSubmitter {
//    val tutorialList = MutableLiveData<MutableList<Int>>().default(Config.tutorials)
//    val tutorialTitleList = MutableLiveData<MutableList<Int>>().default(Config.tutorialTitles)
    val onClickListener = MutableLiveData<View.OnClickListener>()
}