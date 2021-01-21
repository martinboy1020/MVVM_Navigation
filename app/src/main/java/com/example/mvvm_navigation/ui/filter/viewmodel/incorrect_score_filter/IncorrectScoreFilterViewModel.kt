package com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.ui.filter.IncorrectScoreFilterAdapter

class IncorrectScoreFilterViewModel constructor(
    application: Application,
    context: Context,
    val model: IncorrectScoreFilterContract.ModelImpl,
    navController: NavController?
) : BaseViewModel(application, context, navController), IncorrectScoreFilterContract.ViewModelImpl, IncorrectScoreFilterAdapter.IncorrectScoreFilterAdapterOnChangeListener {

    private val submitter =
        IncorrectScoreFilterFragmentSubmitter()

    init {
        this.submitter.incorrectScoreList.value = this.model.getIncorrectDataList()
        this.submitter.incorrectScoreListListener.value = this
    }

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
    override fun changeStatus(id: Int, name: String, isCheck: Boolean) {

    }

}