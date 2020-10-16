package com.example.mvvm_navigation.di

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.dexlight.di.Contract
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.ui.main.MainFragment
import com.example.mvvm_navigation.ui.main.SecondFragment
import com.example.mvvm_navigation.ui.main.ThirdFragment
import com.example.mvvm_navigation.ui.main.model.MainModel
import com.example.mvvm_navigation.ui.main.model.SecondModel
import com.example.mvvm_navigation.ui.main.model.ThirdModel
import com.example.mvvm_navigation.ui.main.vm.main.MainViewModel
import com.example.mvvm_navigation.ui.main.vm.second.SecondViewModel
import com.example.mvvm_navigation.ui.main.vm.third.ThirdViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import com.example.mvvm_navigation.ui.main.vm.main.MainContract.ViewModelImpl as MainViewModelImpl
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract.ViewModelImpl as SecondViewModelImpl
import com.example.mvvm_navigation.ui.main.vm.third.ThirdContract.ViewModelImpl as ThirdViewModelImpl

val mainModule = Kodein.Module(Contract.ModuleName.MAIN){
    bind<MainViewModelImpl>() with singleton {
        getMainViewModel(instance(MainFragment().TAG))
    }
}

private fun getMainViewModel(fragment: MainFragment): MainViewModelImpl{
    val repository = Repository.getInstance(fragment.context!!)
    val model = MainModel.getInstance(repository)
    val factory = MainViewModel.Factory(fragment.activity!!.application, fragment.context!!, model, findNavController(fragment))
    return ViewModelProvider(fragment, factory).get(MainViewModel::class.java)
}

val secondModule = Kodein.Module(Contract.ModuleName.SECOND){
    bind<SecondViewModelImpl>() with singleton {
        getSecondViewModel(instance(SecondFragment().TAG))
    }
}

private fun getSecondViewModel(fragment: SecondFragment): SecondViewModelImpl{
    val repository = Repository.getInstance(fragment.context!!)
    val model = SecondModel.getInstance(repository)
    val factory = SecondViewModel.Factory(fragment.activity!!.application, fragment.context!!, model, findNavController(fragment))
    return ViewModelProvider(fragment, factory).get(SecondViewModel::class.java)
}

val thirdModule = Kodein.Module(Contract.ModuleName.THIRD){
    bind<ThirdViewModelImpl>() with singleton {
        getThirdViewModel(instance(ThirdFragment().TAG))
    }
}

private fun getThirdViewModel(fragment: ThirdFragment): ThirdViewModelImpl{
    val repository = Repository.getInstance(fragment.context!!)
    val model = ThirdModel.getInstance(repository)
    val factory = ThirdViewModel.Factory(fragment.activity!!.application, fragment.context!!, model, findNavController(fragment))
    return ViewModelProvider(fragment, factory).get(ThirdViewModel::class.java)
}