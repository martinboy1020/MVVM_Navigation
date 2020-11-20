package com.example.mvvm_navigation.ui.main

import android.app.SharedElementCallback
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionSet
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        setHasOptionsMenu(true)
        (sharedElementEnterTransition as TransitionSet).addListener((object :
            TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                viewModel.getSubmitter().visible.value = View.VISIBLE
            }
        }))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
    }

}