package com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter

import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.IncorrectScoreData
import com.example.mvvm_navigation.ui.filter.IncorrectScoreFilterAdapter

class IncorrectScoreFilterFragmentSubmitter {

    val incorrectScoreList = MutableLiveData<MutableList<IncorrectScoreData>>().default(
        mutableListOf()
    )
    val sbAppearRateVisible = MutableLiveData<Int>().default(View.VISIBLE)
    val layoutContinueMatchVisible = MutableLiveData<Int>().default(View.GONE)

    val incorrectScoreListListener = MutableLiveData<IncorrectScoreFilterAdapter.IncorrectScoreFilterAdapterOnChangeListener>()
    val onCheckedChangeListener = MutableLiveData<RadioGroup.OnCheckedChangeListener>()

}