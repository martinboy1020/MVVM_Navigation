package com.example.mvvm_navigation.ui.main.vm.second

import android.view.View
import androidx.lifecycle.MutableLiveData

class SecondFragmentSubmitter {
//    val tutorialList = MutableLiveData<MutableList<Int>>().default(Config.tutorials)
//    val tutorialTitleList = MutableLiveData<MutableList<Int>>().default(Config.tutorialTitles)
    val onClickListener = MutableLiveData<View.OnClickListener>()
}