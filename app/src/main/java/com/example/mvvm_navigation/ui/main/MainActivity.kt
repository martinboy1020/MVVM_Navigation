package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.base.components.LayoutId
import com.example.base.components.NavHosttId
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseActivity
import com.example.mvvm_navigation.databinding.ActivityMainBinding


@LayoutId(R.layout.activity_main)
@NavHosttId(R.id.mainActivityNavHostFragment)
class MainActivity : BaseActivity() {

    private var pagePosition = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationBar(binding as ActivityMainBinding)
    }

    private fun initBottomNavigationBar(binding: ActivityMainBinding) {
        binding.bottomNavigationView.menu.setGroupCheckable(0, false, false)
        binding.bottomNavigationView.menu.getItem(0).isChecked = false
        binding.bottomNavigationView.menu.getItem(1).isChecked = false
        binding.bottomNavigationView.menu.getItem(2).isChecked = true
        binding.bottomNavigationView.menu.getItem(3).isChecked = false
        binding.bottomNavigationView.menu.getItem(4).isChecked = false

//        val navController =
//            Navigation.findNavController(this, R.id.mainActivityNavHostFragment)
//        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.matchListFragment -> {
                    if(pagePosition != 0) {
                        pagePosition = 0
//                        transFragment(R.id.action_to_matchListFragment)
//                        transActivity(R.id.action_to_matchListActivity)
                        transFragment(R.id.action_to_matchListFragment)
                    }
                }
//                R.id.nav2 -> transFragment(R.id.action_match_list)
                R.id.homeFragmentV2 -> {
                    if(pagePosition != 2) {
                        pagePosition = 2
                        transFragment(R.id.action_to_homeFragment)
                    }
                }
//                R.id.nav4 -> transFragment(R.id.action_match_list)
//                R.id.nav5 -> transFragment(R.id.action_match_list)
            }
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.mainActivityNavHostFragment).navigateUp()
    }
}