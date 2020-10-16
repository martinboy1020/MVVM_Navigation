package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.di.mainModule
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.ui.main.vm.main.MainContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_main)
class MainFragment : BaseFragment() {
    val TAG = "MainFragment"

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MainFragment>(TAG) with singleton { this@MainFragment }
        import(mainModule)
    }

    private val viewModel by kodein.instance<MainContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        viewModel.getSubmitter().navHostFragment.value = this.activity?.supportFragmentManager?.findFragmentById(R.id.mainActivityNavHostFragment)
    }

}