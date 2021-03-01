package com.example.mvvm_navigation.ui.main.home

import android.os.Bundle
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.di.recentMatchModule
import com.example.mvvm_navigation.ui.main.home.viewmodel.recent_match.RecentMatchContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

class RecentMatchFragment(val position: Int) : BaseFragment() {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<RecentMatchFragment>(RecentMatchFragment::class.java.simpleName) with singleton { this@RecentMatchFragment }
        import(recentMatchModule)
    }

    private val viewModel by kodein.instance<RecentMatchContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}