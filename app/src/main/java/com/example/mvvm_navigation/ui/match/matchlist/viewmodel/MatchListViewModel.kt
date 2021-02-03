package com.example.mvvm_navigation.ui.match.matchlist.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.ui.match.matchlist.MatchListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchListViewModel constructor(
    application: Application,
    context: Context,
    val model: MatchListContract.ModelImpl,
    navController: NavController?
) : BaseViewModel(application, context, navController), MatchListContract.ViewModelImpl,
    View.OnClickListener, MatchListAdapter.MatchListAdapterItemClickListener {

    private val submitter =
        MatchListFragmentSubmitter()

    interface ViewModelToFragmentListener {
        fun goToMatchDetail(matchId: Int)
    }

    init {
        this.submitter.onClickListener.value = this
        this.submitter.matchListAdapterListener.value = this
//        val initTimeStamp = UserSharePreferences(this@MatchListViewModel.context).matchListDate
//        getMatchList(if(initTimeStamp > 0) initTimeStamp else DateUtils.getTodayDatMillis())
    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            model: MatchListContract.ModelImpl,
            navController: NavController?
        ) =
            MatchListViewModel(
                application,
                context,
                model,
                navController
            )
    }

    class Factory(
        val application: Application,
        val context: Context,
        val model: MatchListContract.ModelImpl,
        val navController: NavController?
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun getSubmitter(): MatchListFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {

        }
    }

    override fun onSetTopClick(data: MatchList.Match) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val matchList = this@MatchListViewModel.model.setMatchItemToTopList(data, getSubmitter().pageType.value!!)
//            withContext(Dispatchers.Main) {
//                this@MatchListViewModel.submitter.matchList.value = matchList
//            }
//        }
        this.submitter.viewModelToFragmentListener.value?.goToMatchDetail(data.matchId)
    }

    override fun changeDate(timestamp: Long) {
        getMatchList(if (timestamp > 0) timestamp else DateUtils.getTodayTimeStamp())
    }

    override fun onClickItem(data: MatchList.Match) {}

    private fun getMatchList(date: Long = DateUtils.getTodayTimeStamp()) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = model.getMatchesList(date, getSubmitter().pageType.value!!)
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.onSuccess -> {
//                        val list = result.data.payload.matches
//                        val filterQuery = listOf(662, 24)
//                        val emptyList = mutableListOf<MatchList.Match>()
//                        for (i in filterQuery.indices) {
//                            val filterList = list.filter { it.leagueId == filterQuery[i] }
//                            emptyList.addAll(filterList as MutableList<MatchList.Match>)
//                        }
//                        emptyList.sortedWith(compareBy({ it.openDate }, { it.matchId }))
                        this@MatchListViewModel.submitter.matchList.value =  result.data.payload.matches
                        this@MatchListViewModel.submitter.areaList.value = result.data.payload.areas
                    }
                    is HttpResult.onError -> {

                    }
                }
            }
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = model.getTestMatchesList()
//            withContext(Dispatchers.Main) {
//               if(result != null) this@MatchListViewModel.submitter.matchList.value = result.payload.matches
//            }
//        }
    }


}