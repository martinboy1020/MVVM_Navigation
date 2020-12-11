package com.example.mvvm_navigation.ui.main.vm.third

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.ui.main.MatchListAdapter

class ThirdFragmentSubmitter {
    val visible = MutableLiveData<Int>().default(View.GONE)
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val name = MutableLiveData<String>().default("")
    val realName = MutableLiveData<String>().default("")
    val email = MutableLiveData<String>().default("")
    val matchList = MutableLiveData<MutableList<MatchListItem>>().default(mutableListOf())
    val matchListAdapterListener = MutableLiveData<MatchListAdapter.MatchListAdapterItemClickListener>()
}