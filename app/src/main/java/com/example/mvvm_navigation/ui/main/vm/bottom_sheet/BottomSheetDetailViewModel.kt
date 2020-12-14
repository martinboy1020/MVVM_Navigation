package com.example.mvvm_navigation.ui.main.vm.bottom_sheet

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.network.HttpResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetDetailViewModel constructor(application: Application, context: Context, private val listener: BottomSheetDetailViewModelImpl, val model: BottomSheetDetailContract.ModelImpl, navController: NavController) : BaseViewModel(application, context, navController), BottomSheetDetailContract.ViewModelImpl, View.OnClickListener {

    private val submitter = BottomSheetDetailFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
        this.submitter.betList.value = model.getBetList()
    }

    interface BottomSheetDetailViewModelImpl {
        fun closeDialog()
    }

    companion object{
        fun getInstance(application: Application, context: Context, listener: BottomSheetDetailViewModelImpl, model: BottomSheetDetailContract.ModelImpl, navController: NavController) =
            BottomSheetDetailViewModel(
                application,
                context,
                listener,
                model,
                navController
            )
    }

    override fun getSubmitter(): BottomSheetDetailFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_close ->{
                listener.closeDialog()
            }
        }
    }

    class Factory(val application: Application, val context: Context, val listener: BottomSheetDetailViewModelImpl, val model: BottomSheetDetailContract.ModelImpl, val navController: NavController): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            listener,
            model,
            navController
        ) as T
    }
}