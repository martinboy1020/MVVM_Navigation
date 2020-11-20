package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.databinding.FragmentSecondBinding
import com.example.mvvm_navigation.di.secondModule
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract
import com.example.mvvm_navigation.ui.main.vm.second.SecondViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_second)
class SecondFragment : BaseFragment(), SecondViewModel.SecondViewImpl {

    val TAG = "SecondFragment"

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<SecondFragment>(TAG) with singleton { this@SecondFragment }
        import(secondModule)
    }

    private val viewModel by kodein.instance<SecondContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
    }

    override fun transPage(navController: NavController) {
        val view = (binding as FragmentSecondBinding).testView
        val extras = FragmentNavigatorExtras(view to "header_view")
        navController.navigate(R.id.action_secondFragment_to_thirdFragment, null, null, extras)
    }

}