package com.example.mvvm_navigation.base

import android.app.Application
import android.content.Context
import androidx.navigation.NavController
import com.example.base.rigger.ViewModelRigger

open class BaseViewModel constructor(application: Application, context: Context, navController: NavController?) : ViewModelRigger(application, context, navController) {

}