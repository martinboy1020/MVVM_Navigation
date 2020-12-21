package com.example.mvvm_navigation.ui.main.home.viewmodel.home

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.datacenter.data.GoalAndLostData
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.ui.main.home.HomeFragmentDirections
import com.example.mvvm_navigation.ui.main.matchlist.MatchListAdapter
import com.example.mvvm_navigation.widget.BannerWidget
import com.example.mvvm_navigation.widget.GoalAndLostDataWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel constructor(
    application: Application,
    context: Context,
    val model: HomeContract.ModelImpl,
    navController: NavController
) : BaseViewModel(application, context, navController), HomeContract.ViewModelImpl,
    View.OnClickListener, BannerWidget.BannerClickListener,
    MatchListAdapter.MatchListAdapterItemClickListener,
    RadioGroup.OnCheckedChangeListener {

    private lateinit var timer: CountDownTimer

    private val submitter =
        HomeFragmentSubmitter()

    init {
        if (UserSharePreferences(context).userToken.isEmpty()) userLogin() else tokenRefresh()
        this.submitter.onClickListener.value = this
        val goalData = GoalAndLostData(GoalAndLostDataWidget.Type.GOAL, 2.5f, 0f)
        val lostData = GoalAndLostData(GoalAndLostDataWidget.Type.LOST, 0f, 3.1f)
        this.submitter.goalData.value = goalData
        this.submitter.lostData.value = lostData
        this.submitter.bannerClickListener.value = this
        this.submitter.matchList.value = model.getMatchList()
        this.submitter.matchListClickListener.value = this
        this.submitter.matchFilterClickListener.value = this
        CoroutineScope(Dispatchers.IO).launch {
            val data = model.getBannerData()
            withContext(Dispatchers.Main) {
                getSubmitter().bannerData.value = data
            }
        }
    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            model: HomeContract.ModelImpl,
            navController: NavController
        ) =
            HomeViewModel(
                application,
                context,
                model,
                navController
            )
    }

    override fun drawerNavigationClick(itemId: Int) {
        when (itemId) {
            R.id.action_match_list -> {
                this.transFragment(R.id.action_homeFragment_to_matchListFragment)
            }
        }
    }

    override fun getSubmitter(): HomeFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {
//            R.id.btn_go_to_second_fragment ->{
//            //Need args
//                val action = TutorialFragmentDirections.actionTutorialFragmentToWelcomeFragment("title")
//                this.transFragment(action)
//
//            //No args
//                this.transFragment(R.id.action_mainFragment_to_secondFragment)
//            }
//            R.id.btn_go_to_test_dynamic_feature_fragment -> {
//                this.transFragment(R.id.action_homeFragment_to_testDynamicFeatureFragment)
//            }
        }
    }

    private fun userLogin() {
        Toast.makeText(this@HomeViewModel.context, "帳號登入", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val login = model.userLogin("PMQ102", "a111111", 1)
            withContext(Dispatchers.Main) {
                when (login) {
                    is HttpResult.onSuccess -> {
                        Toast.makeText(this@HomeViewModel.context, "登入成功", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is HttpResult.onError -> {
                        Toast.makeText(
                            this@HomeViewModel.context,
                            "登入失敗 ${login.errorCode}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun tokenRefresh() {
        Toast.makeText(this@HomeViewModel.context, "刷新憑證", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val refresh = model.userTokenRefresh(UserSharePreferences(context).userToken)
            withContext(Dispatchers.Main) {
                when (refresh) {
                    is HttpResult.onSuccess -> {
                        Toast.makeText(this@HomeViewModel.context, "刷新成功", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is HttpResult.onError -> {
                        Toast.makeText(
                            this@HomeViewModel.context,
                            "刷新失敗 ${refresh.errorCode}",
                            Toast.LENGTH_SHORT
                        ).show()
                        userLogin()
                    }
                }
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

    class Factory(
        val application: Application,
        val context: Context,
        val model: HomeContract.ModelImpl,
        val navController: NavController
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            model,
            navController
        ) as T
    }

    override fun click(position: Int) {
        Toast.makeText(this.context, "Banner Position: $position", Toast.LENGTH_SHORT).show()
    }

    override fun onSetTopClick(data: MatchListItem) {

    }

    override fun onClickItem(data: MatchListItem) {
        val action = HomeFragmentDirections.actionHomeFragmentOpenBottomSheetDetail(
            this.submitter.goalData.value!!,
            this.submitter.lostData.value!!
        )
        this.transFragment(action)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val radBtn = p0?.findViewById<RadioButton>(p1)
        Toast.makeText(this.context, "Filter is ${radBtn?.text}", Toast.LENGTH_SHORT).show()
    }
}