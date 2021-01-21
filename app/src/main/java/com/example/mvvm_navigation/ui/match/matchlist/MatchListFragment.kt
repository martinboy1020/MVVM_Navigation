package com.example.mvvm_navigation.ui.match.matchlist

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.di.matchListModule
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_matchlist)
class MatchListFragment(var status: Int) : BaseFragment() {

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
    }

    override fun onResume() {
        super.onResume()
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

}