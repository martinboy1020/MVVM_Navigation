package com.example.mvvm_navigation.ui.main.vm.third

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default

class ThirdFragmentSubmitter {
    val visible = MutableLiveData<Int>().default(View.GONE)
    val onClickListener = MutableLiveData<View.OnClickListener>()
}