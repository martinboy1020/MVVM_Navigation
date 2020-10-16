package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.di.secondModule
import com.example.mvvm_navigation.di.thirdModule
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract
import com.example.mvvm_navigation.ui.main.vm.third.ThirdContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_third)
class ThirdFragment : BaseFragment() {

    val TAG = "ThirdFragment"

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<ThirdFragment>(TAG) with singleton { this@ThirdFragment }
        import(thirdModule)
    }

    private val viewModel by kodein.instance<ThirdContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
    }

}