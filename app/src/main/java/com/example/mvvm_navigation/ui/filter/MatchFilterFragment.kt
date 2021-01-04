package com.example.mvvm_navigation.ui.filter

import com.example.base.components.LayoutId
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.di.matchFilterModule
import com.example.mvvm_navigation.ui.filter.viewmodel.match_filter.MatchFilterContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_match_filter)
class MatchFilterFragment : BaseFragment() {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MatchFilterFragment>(MatchFilterFragment::class.java.simpleName) with singleton { this@MatchFilterFragment }
        import(matchFilterModule)
    }

    private val viewModel by kodein.instance<MatchFilterContract.ViewModelImpl>()

}