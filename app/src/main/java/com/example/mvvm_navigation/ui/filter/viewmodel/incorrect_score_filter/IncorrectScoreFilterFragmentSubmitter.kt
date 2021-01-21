package com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter

import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.IncorrectScoreData
import com.example.mvvm_navigation.ui.filter.IncorrectScoreFilterAdapter

class IncorrectScoreFilterFragmentSubmitter {

    val incorrectScoreList = MutableLiveData<MutableList<IncorrectScoreData>>().default(
        mutableListOf()
    )

    val incorrectScoreListListener = MutableLiveData<IncorrectScoreFilterAdapter.IncorrectScoreFilterAdapterOnChangeListener>()

}