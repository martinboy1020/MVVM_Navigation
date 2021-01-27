package com.example.mvvm_navigation.ui.match_detail.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.network.HttpResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchDetailViewModel constructor(
    application: Application,
    context: Context,
    val model: MatchDetailContract.ModelImpl,
    navController: NavController?
) : BaseViewModel(application, context, navController), MatchDetailContract.ViewModelImpl,
    View.OnClickListener {

    private val submitter =
        MatchDetailFragmentSubmitter()

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            model: MatchDetailContract.ModelImpl,
            navController: NavController?
        ) =
            MatchDetailViewModel(
                application,
                context,
                model,
                navController
            )
    }

    class Factory(
        val application: Application,
        val context: Context,
        val model: MatchDetailContract.ModelImpl,
        val navController: NavController?
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun getSubmitter(): MatchDetailFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {

        }
    }

    override fun getMatchDetail(matchId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = this@MatchDetailViewModel.model.getMatchDetail(matchId)
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.onSuccess -> {
//                        this@MatchDetailViewModel.submitter.matchDetail.value = result.data.payload
                        Log.d("tag123456789", "result.data.payload: " + result.data.payload)
                        this@MatchDetailViewModel.submitter.matchDetailString.value =
                            result.data.payload.homeName + " vs " + result.data.payload.awayName
                    }
                    is HttpResult.onError -> {
                        Log.d("tag123456789", "result.errorMsg: " + result.errorMsg)
                    }
                }
            }
        }
    }

}