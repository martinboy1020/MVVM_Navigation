package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import com.example.base.components.LayoutId
import com.example.base.components.NavHosttId
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseActivity

@LayoutId(R.layout.activity_main)
@NavHosttId(R.id.activity_main_container)
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}