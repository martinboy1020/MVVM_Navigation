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
import com.example.mvvm_navigation.datacenter.data.LeagueTeamData
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.Home
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.ui.main.home.HomeFragmentDirections
import com.example.mvvm_navigation.ui.main.home.MatchesRecentAdapter
import com.example.mvvm_navigation.widget.BannerWidget
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
    MatchesRecentAdapter.MatchListAdapterItemClickListener,
    RadioGroup.OnCheckedChangeListener,
    CompoundButton.OnCheckedChangeListener {

    private val submitter =
        HomeFragmentSubmitter()

    init {
        if (UserSharePreferences(context).userToken.isEmpty()) userLogin() else tokenRefresh()
        this.submitter.onClickListener.value = this
        this.submitter.bannerClickListener.value = this
        this.submitter.matchesRecentClickListener.value = this
        this.submitter.matchFilterClickListener.value = this
        this.submitter.topListBtnClickListener.value = this
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

        const val timeKey4HR = "fourHours"
        const val timeKey8HR = "eightHours"
        const val timeKey12HR = "twelveHours"
        const val timeKey24HR = "twentyFourHours"
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

    override fun drawerNavigationClick(itemId: Int) {
        when (itemId) {
            R.id.action_match_list -> {
//                this.transFragment(R.id.action_homeFragment_to_matchListFragment)
                this.transFragment(R.id.action_homeFragment_to_matchListActivity)
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

    override fun userLogin() {
        Toast.makeText(this@HomeViewModel.context, "帳號登入", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val login = model.userLogin("PMQ102", "a111111", 1)
            withContext(Dispatchers.Main) {
                when (login) {
                    is HttpResult.onSuccess -> {
                        Toast.makeText(this@HomeViewModel.context, "登入成功", Toast.LENGTH_SHORT)
                            .show()
                        getHomeInfo()
                        getTgMatchRecent()
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

    override fun tokenRefresh() {
        Toast.makeText(this@HomeViewModel.context, "刷新憑證", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val refresh = model.userTokenRefresh(UserSharePreferences(context).userToken)
            withContext(Dispatchers.Main) {
                when (refresh) {
                    is HttpResult.onSuccess -> {
                        Toast.makeText(this@HomeViewModel.context, "刷新成功", Toast.LENGTH_SHORT)
                            .show()
                        getHomeInfo()
                        getTgMatchRecent()
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

    private fun getHomeInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val homeInfo = model.getHomeInfo()
            withContext(Dispatchers.Main) {
                when (homeInfo) {
                    is HttpResult.onSuccess -> {
                        Toast.makeText(this@HomeViewModel.context, "獲取首頁資訊成功", Toast.LENGTH_SHORT)
                            .show()
                        showBanner(homeInfo.data.payload)
                    }
                    is HttpResult.onError -> {
                        Toast.makeText(
                            this@HomeViewModel.context,
                            "獲取首頁資訊失敗 ${homeInfo.errorCode}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun showBanner(homeInfo: Home.WebHomeInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = model.getBannerData(homeInfo)
            withContext(Dispatchers.Main) {
                getSubmitter().bannerData.value = data
            }
        }
    }

    private fun getTgMatchRecent(timeKey: String = timeKey12HR) {
        this@HomeViewModel.submitter.recentMatchTimeKeyBtnClickable.value = false
        CoroutineScope(Dispatchers.IO).launch {
            val tgMatchesRecent = model.getTgMatchesRecent(timeKey)
            withContext(Dispatchers.Main) {
                this@HomeViewModel.submitter.recentMatchTimeKeyBtnClickable.value = true
                when (tgMatchesRecent) {
                    is HttpResult.onSuccess -> {
                        this@HomeViewModel.submitter.matchesRecentList.value =
                            tgMatchesRecent.data.payload
                    }
                    is HttpResult.onError -> {
                        Toast.makeText(
                            this@HomeViewModel.context,
                            "獲取即將開賽列表失敗 ${tgMatchesRecent.errorCode}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun click(position: Int) {
        Toast.makeText(this.context, "Banner Position: $position", Toast.LENGTH_SHORT).show()
    }

    override fun onSetTopClick(data: TgMatchRecent.Recent) {

    }

    override fun onClickItem(data: TgMatchRecent.Recent) {
        val leagueTeamData = LeagueTeamData(
            data.leagueId,
            data.league,
            data.leagueLogo,
            data.homeId,
            data.home,
            data.homeLogo,
            data.awayId,
            data.away,
            data.awayLogo,
            data.openDate
        )
        val action = HomeFragmentDirections.actionHomeFragmentOpenBottomSheetDetail(
            leagueTeamData
        )
        this.transFragment(action)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val radBtn = p0?.findViewById<RadioButton>(p1)
        when (radBtn?.id) {
            R.id.btn_4hr -> {
                getTgMatchRecent(timeKey4HR)
            }
            R.id.btn_8hr -> {
                getTgMatchRecent(timeKey8HR)
            }
            R.id.btn_12hr -> {
                getTgMatchRecent(timeKey12HR)
            }
            R.id.btn_24hr -> {
                getTgMatchRecent(timeKey24HR)
            }
        }
        Toast.makeText(this.context, "Filter is ${radBtn?.text}", Toast.LENGTH_SHORT).show()
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        if(p0?.id == R.id.tv_top) {
            when(p1) {
                true -> {

                }
                false -> {

                }
            }

        }
    }
}