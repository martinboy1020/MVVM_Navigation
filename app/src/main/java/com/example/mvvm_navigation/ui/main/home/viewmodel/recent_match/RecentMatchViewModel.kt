package com.example.mvvm_navigation.ui.main.home.viewmodel.recent_match

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.data.LeagueTeamData
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.Home
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.ui.main.home.HomeFragmentDirections
import com.example.mvvm_navigation.ui.main.home.MatchesRecentAdapter
import com.example.mvvm_navigation.widget.BannerWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentMatchViewModel constructor(
    application: Application,
    context: Context,
    val model: RecentMatchContract.ModelImpl,
    navController: NavController
) : BaseViewModel(application, context, navController), RecentMatchContract.ViewModelImpl, View.OnClickListener {

    private val submitter =
        RecentMatchFragmentSubmitter()

    private var nowTimeKey = timeKey12HR

    init {

    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            model: RecentMatchContract.ModelImpl,
            navController: NavController
        ) =
            RecentMatchViewModel(
                application,
                context,
                model,
                navController
            )

        const val timeKey4HR = "fourHours"
        const val timeKey8HR = "eightHours"
        const val timeKey12HR = "twelveHours"
        const val timeKey24HR = "twentyFourHours"
    }

    class Factory(
        val application: Application,
        val context: Context,
        val model: RecentMatchContract.ModelImpl,
        val navController: NavController
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun getSubmitter(): RecentMatchFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {

        }
    }

}