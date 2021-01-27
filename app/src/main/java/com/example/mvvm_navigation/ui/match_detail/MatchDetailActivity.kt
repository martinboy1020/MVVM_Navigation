package com.example.mvvm_navigation.ui.match_detail

import android.os.Bundle
import androidx.navigation.NavArgument
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_filter.*

@LayoutId(R.layout.activity_match_detail)
class MatchDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        // 將Bundle傳給第一個顯示的Fragment
        val navHostFragment = filterActivityNavHostFragment as DynamicNavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.match_detail)
        if (intent.extras != null) {
            val argument = intent.extras?.get("matchId")
            val navArgument = NavArgument.Builder().setDefaultValue(argument).build()
            graph.addArgument("matchId", navArgument)
            navHostFragment.navController.graph = graph
        }
    }
}