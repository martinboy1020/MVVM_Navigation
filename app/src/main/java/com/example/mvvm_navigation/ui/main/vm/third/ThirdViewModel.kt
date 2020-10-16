package com.example.mvvm_navigation.ui.main.vm.third

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel

class ThirdViewModel constructor(application: Application, context: Context, val model: ThirdContract.ModelImpl, navController: NavController) : BaseViewModel(application, context, navController), ThirdContract.ViewModelImpl, View.OnClickListener {

    private val submitter =
        ThirdFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
    }

    companion object{
        fun getInstance(application: Application, context: Context, model: ThirdContract.ModelImpl, navController: NavController) =
            ThirdViewModel(
                application,
                context,
                model,
                navController
            )
    }

    override fun getSubmitter(): ThirdFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_go_to_main_fragment ->{
                //Need args
//                val action = TutorialFragmentDirections.actionTutorialFragmentToWelcomeFragment("title")
//                this.transFragment(action)

                //No args
                this.transFragment(R.id.action_thirdFragment_to_mainFragment)
            }
        }
    }

    class Factory(val application: Application, val context: Context, val model: ThirdContract.ModelImpl, val navController: NavController): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }
}