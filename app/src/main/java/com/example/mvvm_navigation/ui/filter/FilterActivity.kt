package com.example.mvvm_navigation.ui.filter

import android.os.Bundle
import androidx.navigation.NavArgument
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.fragment.NavHostFragment
import com.example.base.components.LayoutId
import com.example.base.components.NavHosttId
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_filter.*

@LayoutId(R.layout.activity_filter)
@NavHosttId(R.id.filterActivityNavHostFragment)
class FilterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        // 將Bundle傳給第一個顯示的Fragment
        val navHostFragment = filterActivityNavHostFragment as DynamicNavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.filter)
        if (intent.extras != null) {
            val argument = intent.extras?.get("filter")
            val navArgument = NavArgument.Builder().setDefaultValue(argument).build()
            graph.addArgument("filter", navArgument)
            navHostFragment.navController.graph = graph
        }
    }
}