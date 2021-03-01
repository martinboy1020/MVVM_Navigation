package com.example.mvvm_navigation.ui.match.matchlist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.datacenter.network.response.MatchDetail
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.di.matchListModule
import com.example.mvvm_navigation.ui.filter.FilterActivity
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListContract
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListViewModel
import com.example.mvvm_navigation.ui.match_detail.MatchDetailActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton
import java.lang.reflect.Type

@LayoutId(R.layout.fragment_matchlist)
class MatchListFragment(var status: Int = 2) : BaseFragment(), MatchListViewModel.ViewModelToFragmentListener {

    private var firstEntry = true

    companion object {
        const val MATCH_ING = 0
        const val MATCH_UNOPEN = 1
        const val MATCH_ENDING = 2
        const val MATCH_TOP = 3
    }

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MatchListFragment>(MatchListFragment::class.java.simpleName) with singleton { this@MatchListFragment }
        import(matchListModule)
    }

    private val viewModel by kodein.instance<MatchListContract.ViewModelImpl>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.getSubmitter().viewModelToFragmentListener.value = this
    }

    override fun onResume() {
        super.onResume()
        Log.d("tag123456789", "MatchListFragment changeDate")
        this.viewModel.changeDate(UserSharePreferences(this.requireContext()).matchListDate)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.viewModel.getSubmitter().pageType.value = status
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
    }

    fun changeDate(timestamp: Long) {
        this.viewModel.changeDate(timestamp)
    }

    fun getAreaList(): MutableList<MatchList.Area>? {
        return this.viewModel.getSubmitter().areaList.value
    }

    override fun goToMatchDetail(matchId: Int) {
        val intent = Intent(this.requireContext(), MatchDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("matchId", matchId)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}