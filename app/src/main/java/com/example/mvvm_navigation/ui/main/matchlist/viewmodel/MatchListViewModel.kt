package com.example.mvvm_navigation.ui.main.matchlist.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.ui.main.matchlist.MatchListAdapter

class MatchListViewModel constructor(application: Application, context: Context, val model: MatchListContract.ModelImpl, navController: NavController) : BaseViewModel(application, context, navController), MatchListContract.ViewModelImpl, View.OnClickListener, MatchListAdapter.MatchListAdapterItemClickListener {

    private val submitter =
        MatchListFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
        this.submitter.matchListAdapterListener.value = this
        this.submitter.matchList.value = this.model.getMatchList()
    }

    companion object{
        fun getInstance(application: Application, context: Context, model: MatchListContract.ModelImpl, navController: NavController) =
            MatchListViewModel(
                application,
                context,
                model,
                navController
            )
    }

    override fun getSubmitter(): MatchListFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when(view!!.id){

        }
    }

    class Factory(val application: Application, val context: Context, val model: MatchListContract.ModelImpl, val navController: NavController): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun onSetTopClick(data: MatchListItem) {
        this.submitter.matchList.value = this.model.setMatchItemToTopList(data)
    }

    override fun onClickItem(data: MatchListItem) {

    }


}