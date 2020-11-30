package com.example.mvvm_navigation.ui.main.vm.second

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.SecondAdapter
import com.example.mvvm_navigation.utils.default

class SecondFragmentSubmitter {
    val userListData = MutableLiveData<List<UserData.User>>().default(mutableListOf())
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val userListItemListener = MutableLiveData<SecondAdapter.SecondAdapterItemClickListener>()
}