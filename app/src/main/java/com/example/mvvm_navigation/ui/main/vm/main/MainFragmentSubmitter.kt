package com.example.mvvm_navigation.ui.main.vm.main

import android.opengl.Visibility
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default

class MainFragmentSubmitter {
//    val tutorialList = MutableLiveData<MutableList<Int>>().default(Config.tutorials)
//    val tutorialTitleList = MutableLiveData<MutableList<Int>>().default(Config.tutorialTitles)
    val number = MutableLiveData<Int>().default(1)
    val buttonVisible = MutableLiveData<Int>().default(View.GONE)
    val navHostFragment = MutableLiveData<Fragment>()
    val onClickListener = MutableLiveData<View.OnClickListener>()
}