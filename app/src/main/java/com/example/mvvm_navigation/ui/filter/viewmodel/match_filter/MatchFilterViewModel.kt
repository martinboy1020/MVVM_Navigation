package com.example.mvvm_navigation.ui.filter.viewmodel.match_filter

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.utils.LogUtils
import com.example.mvvm_navigation.widget.match_filter_widget.MatchFilterWidget

class MatchFilterViewModel constructor(
    application: Application,
    context: Context,
    val model: MatchFilterContract.ModelImpl,
    navController: NavController?
) : BaseViewModel(application, context, navController), MatchFilterContract.ViewModelImpl, MatchFilterWidget.MatchFilterWidgetListener {

    private val submitter =
        MatchFilterFragmentSubmitter()

    init {
        this.submitter.matchFilterWidgetListener.value = this
    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            model: MatchFilterContract.ModelImpl,
            navController: NavController?
        ) =
            MatchFilterViewModel(
                application,
                context,
                model,
                navController
            )
    }

    class Factory(
        val application: Application,
        val context: Context,
        val model: MatchFilterContract.ModelImpl,
        val navController: NavController?
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun setMatchFilter(mutableList: MutableList<MatchList.Area>) {
        this.submitter.areaList.value = mutableList
    }

    override fun getSubmitter(): MatchFilterFragmentSubmitter = this.submitter
    override fun returnSelectedLeague(selectedLeagueList: MutableList<Int>) {
        this.submitter.selectedLeagues.value = selectedLeagueList
    }

}