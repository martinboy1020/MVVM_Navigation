package com.example.mvvm_navigation.ui.main.home

import android.os.Bundle
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.databinding.FragmentHomeBinding
import com.example.mvvm_navigation.databinding.FragmentHomeV2Binding
import com.example.mvvm_navigation.di.homeV2Module
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton


@LayoutId(R.layout.fragment_home_v2)
class HomeFragmentV2 : BaseFragment() {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<HomeFragmentV2>(HomeFragmentV2::class.java.simpleName) with singleton { this@HomeFragmentV2 }
        import(homeV2Module)
    }

    private val viewModel by kodein.instance<HomeContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        viewModel.getSubmitter().navHostFragment.value =
            this.activity?.supportFragmentManager?.findFragmentById(R.id.mainActivityNavHostFragment)
    }

    override fun onResume() {
        super.onResume()
        (this.binding as FragmentHomeV2Binding).bannerWidget.setBannerTurning(true)
        this.viewModel.getTgMatchRecent()
    }

    override fun onPause() {
        super.onPause()
        (this.binding as FragmentHomeV2Binding).bannerWidget.setBannerTurning(false)
    }

}