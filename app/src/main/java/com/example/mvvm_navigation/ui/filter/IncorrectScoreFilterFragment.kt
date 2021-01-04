package com.example.mvvm_navigation.ui.filter

import com.example.base.components.LayoutId
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.di.incorrectScoreFilterModule
import com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter.IncorrectScoreFilterContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_incorrect_score_filter)
class IncorrectScoreFilterFragment : BaseFragment() {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<IncorrectScoreFilterFragment>(IncorrectScoreFilterFragment::class.java.simpleName) with singleton { this@IncorrectScoreFilterFragment }
        import(incorrectScoreFilterModule)
    }

    private val viewModel by kodein.instance<IncorrectScoreFilterContract.ViewModelImpl>()

}

