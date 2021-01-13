package com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.base.BaseViewModel

class IncorrectScoreFilterViewModel constructor(
    application: Application,
    context: Context,
    val model: IncorrectScoreFilterContract.ModelImpl,
    navController: NavController?
) : BaseViewModel(application, context, navController), IncorrectScoreFilterContract.ViewModelImpl {

    private val submitter =
        IncorrectScoreFilterFragmentSubmitter()

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            model: IncorrectScoreFilterContract.ModelImpl,
            navController: NavController?
        ) =
            IncorrectScoreFilterViewModel(
                application,
                context,
                model,
                navController
            )
    }

    class Factory(
        val application: Application,
        val context: Context,
        val model: IncorrectScoreFilterContract.ModelImpl,
        val navController: NavController?
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun getSubmitter(): IncorrectScoreFilterFragmentSubmitter = this.submitter

}