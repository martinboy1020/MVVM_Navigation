package com.example.mvvm_navigation.ui.main.vm.main

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel

class MainViewModel constructor(application: Application, context: Context, val model: MainContract.ModelImpl, navController: NavController) : BaseViewModel(application, context, navController), MainContract.ViewModelImpl, View.OnClickListener {

    private val submitter =
        MainFragmentSubmitter()
    private lateinit var timer: CountDownTimer

    init {
        this.submitter.onClickListener.value = this
        startCountDown()
    }

    companion object{
        fun getInstance(application: Application, context: Context, model: MainContract.ModelImpl, navController: NavController) =
            MainViewModel(
                application,
                context,
                model,
                navController
            )
    }

    override fun getSubmitter(): MainFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_go_to_second_fragment ->{
                //Need args
//                val action = TutorialFragmentDirections.actionTutorialFragmentToWelcomeFragment("title")
//                this.transFragment(action)

                //No args
                this.transFragment(R.id.action_mainFragment_to_secondFragment)
            }
        }
    }

    private fun startCountDown() {
        timer = object : CountDownTimer(5 * 1000L, 1000) {
            override fun onFinish() {
                submitter.number.value = 2
                submitter.buttonVisible.value = View.VISIBLE
            }
            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }

    class Factory(val application: Application, val context: Context, val model: MainContract.ModelImpl, val navController: NavController): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }
}