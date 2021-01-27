package com.example.mvvm_navigation.ui.match_detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.di.matchDetailModule
import com.example.mvvm_navigation.di.matchListModule
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListContract
import com.example.mvvm_navigation.ui.match_detail.viewmodel.MatchDetailContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_match_detail)
class MatchDetailFragment : BaseFragment() {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MatchDetailFragment>(MatchDetailFragment::class.java.simpleName) with singleton { this@MatchDetailFragment }
        import(matchDetailModule)
    }

    private val viewModel by kodein.instance<MatchDetailContract.ViewModelImpl>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        val matchId = bundle?.getInt("matchId", -1)
        if(matchId != null && matchId != -1) this.viewModel.getMatchDetail(matchId)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
    }

}