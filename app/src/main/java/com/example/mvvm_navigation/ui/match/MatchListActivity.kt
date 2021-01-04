package com.example.mvvm_navigation.ui.match

import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.base.components.LayoutId
import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseActivity
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.ui.match.matchlist.MatchListFragment
import com.example.mvvm_navigation.widget.LogoToolBarWidget
import com.example.mvvm_navigation.widget.MatchListToolBarWidget
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_match_list.*
import kotlinx.android.synthetic.main.activity_match_list.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

@LayoutId(R.layout.activity_match_list)
class MatchListActivity : BaseActivity(), MatchListToolBarWidget.ChangeDateListener {

    private var pageAdapter: MatchListFragmentPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vpMatchListFragment: ViewPager2 = binding.root.vp_match_list_fragment
        pageAdapter = MatchListFragmentPageAdapter(supportFragmentManager, lifecycle)
        vpMatchListFragment.adapter = pageAdapter
        vpMatchListFragment.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val matchListFragment = supportFragmentManager.findFragmentByTag("f" + vp_match_list_fragment.currentItem)
                Log.d("tag12345", "matchListFragment $position: $matchListFragment")
                if(matchListFragment is MatchListFragment) matchListFragment.changeDate(UserSharePreferences(this@MatchListActivity).matchListDate)
            }
        })
        val tabLayout: TabLayout = binding.root.tabLayout
        val title: ArrayList<String> = arrayListOf("進行中", "未開始", "已完賽", "置頂")
        TabLayoutMediator(tabLayout, vpMatchListFragment) { tab, position ->
            tab.text = title[position]
        }.attach()

        val matchListToolBarWidget: MatchListToolBarWidget = binding.root.match_list_tool_bar
        matchListToolBarWidget.setListener(this)
    }

    override fun changeDate(timestamp: Long) {
        UserSharePreferences(this).matchListDate = timestamp
        val matchListFragment = supportFragmentManager.findFragmentByTag("f" + vp_match_list_fragment.currentItem)
        if(matchListFragment is MatchListFragment) matchListFragment.changeDate(timestamp)
    }


    override fun onDestroy() {
        super.onDestroy()
        UserSharePreferences(this).removeData(
            UserSharePreferences.PREFERENCE_TABLE_NAME_MATCH,
            UserSharePreferences.KEY_MATCH_LIST_DATE
        )
    }

}