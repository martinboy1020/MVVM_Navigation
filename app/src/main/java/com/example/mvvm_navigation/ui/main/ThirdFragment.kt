package com.example.mvvm_navigation.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.databinding.FragmentThirdBinding
import com.example.mvvm_navigation.di.thirdModule
import com.example.mvvm_navigation.ui.main.vm.third.ThirdContract
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.item_user.view.*
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
//        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            duration = 500
        }
        if(sharedElementEnterTransition != null) {
            setHasOptionsMenu(true)
            (sharedElementEnterTransition as Transition).addListener((object :
                TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    super.onTransitionEnd(transition)
                    viewModel.getSubmitter().visible.value = View.VISIBLE
                }
            }))
        } else {
            viewModel.getSubmitter().visible.value = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transitionName = arguments?.getString("transitionName", "")
        val name = arguments?.getString("name", "")
        val username = arguments?.getString("username", "")
        val email = arguments?.getString("email", "")
        if(!transitionName.isNullOrEmpty()) {
            val binding = this.binding as FragmentThirdBinding
            binding.userItemWidget.transitionName = transitionName
            binding.userItemWidget.setBackgroundColor(ContextCompat.getColor(this.requireContext(), android.R.color.holo_red_light))
            binding.userItemWidget.real_name.text = StringBuilder().append("UserName: ").append(name).toString()
            binding.userItemWidget.real_name.setTextColor(ContextCompat.getColor(this.requireContext(), android.R.color.white))
            binding.userItemWidget.user_name.text = StringBuilder().append("RealName: ").append(username).toString()
            binding.userItemWidget.user_name.setTextColor(ContextCompat.getColor(this.requireContext(), android.R.color.white))
            binding.userItemWidget.email.text = StringBuilder().append("Email: ").append(email).toString()
            binding.userItemWidget.email.setTextColor(ContextCompat.getColor(this.requireContext(), android.R.color.white))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
    }

}