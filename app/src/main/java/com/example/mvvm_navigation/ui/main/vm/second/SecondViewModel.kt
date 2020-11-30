package com.example.mvvm_navigation.ui.main.vm.second

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.SecondAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondViewModel constructor(
    application: Application,
    context: Context,
    val listener: SecondViewImpl,
    val model: SecondContract.ModelImpl,
    navController: NavController
) : BaseViewModel(application, context, navController), SecondContract.ViewModelImpl,
    View.OnClickListener, SecondAdapter.SecondAdapterItemClickListener {

    private val submitter =
        SecondFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
        this.submitter.userListItemListener.value = this
        getUserList()
    }

    interface SecondViewImpl {
        fun transPage(navController: NavController)
        fun transPage(view: View, data: UserData.User, position: Int, navController: NavController)
    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            listener: SecondViewImpl,
            model: SecondContract.ModelImpl,
            navController: NavController
        ) =
            SecondViewModel(
                application,
                context,
                listener,
                model,
                navController
            )
    }

    override fun getSubmitter(): SecondFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_go_to_main_fragment -> {
                //Need args
//                val action = TutorialFragmentDirections.actionTutorialFragmentToWelcomeFragment("title")
//                this.transFragment(action)

                //No args
//                this.transFragment(R.id.action_secondFragment_to_thirdFragment)
                listener.transPage(navController)
            }
        }
    }

    private fun getUserList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = this@SecondViewModel.model.getUser()
            withContext(Dispatchers.Main) {
                when (response) {
                    is HttpResult.onSuccess -> {
                        getSubmitter().userListData.value = response.data
                    }
                    is HttpResult.onError -> {
                    }
                }
            }
        }
    }

    class Factory(
        val application: Application,
        val context: Context,
        val listener: SecondViewImpl,
        val model: SecondContract.ModelImpl,
        val navController: NavController
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            listener,
            model,
            navController
        ) as T
    }

    override fun onItemClick(view: View, data: UserData.User, position: Int) {
        this.listener.transPage(view, data, position, navController)
    }
}