package com.example.mvvm_navigation.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.di.secondModule
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract
import com.example.mvvm_navigation.ui.main.vm.second.SecondViewModel
import kotlinx.android.synthetic.main.fragment_second.view.*
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
        postponeEnterTransition()
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        this.binding.root.test_recycle_view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        setExitSharedElementCallback(object: SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                Log.d("tag12345", "ExitSharedElement sharedElements?.size" + sharedElements?.size)
            }
        })
    }

    override fun transPage(navController: NavController) {
//        val view = (binding as FragmentSecondBinding).testView
//        val extras = FragmentNavigatorExtras(view to "test")
        navController.navigate(R.id.action_secondFragment_to_thirdFragment, null, null, null)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun transPage(
        view: View,
        data: UserData.User,
        position: Int,
        navController: NavController
    ) {
        val extras = FragmentNavigatorExtras(view to view.transitionName)
        val bundle = Bundle()
        bundle.putString("transitionName", view.transitionName)
        bundle.putString("name", data.name)
        bundle.putString("username", data.username)
        bundle.putString("email", data.email)
        navController.navigate(R.id.action_secondFragment_to_thirdFragment, bundle, null, extras)
    }

}