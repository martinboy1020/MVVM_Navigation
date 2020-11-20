package com.example.mvvm_navigation.ui.main.vm.third

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default

class ThirdFragmentSubmitter {
//    val tutorialList = MutableLiveData<MutableList<Int>>().default(Config.tutorials)
//    val tutorialTitleList = MutableLiveData<MutableList<Int>>().default(Config.tutorialTitles)
    val visible = MutableLiveData<Int>().default(View.GONE)
    val onClickListener = MutableLiveData<View.OnClickListener>()
}