package com.example.mvvm_navigation.di

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.dexlight.di.Contract
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.ui.main.home.BottomSheetDetailFragment
import com.example.mvvm_navigation.ui.main.home.HomeFragment
import com.example.mvvm_navigation.ui.main.home.model.BottomSheetDetailModel
import com.example.mvvm_navigation.ui.main.home.model.HomeModel
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailViewModel
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeViewModel
import com.example.mvvm_navigation.ui.main.matchlist.MatchListFragment
import com.example.mvvm_navigation.ui.main.matchlist.model.MatchListModel
import com.example.mvvm_navigation.ui.main.matchlist.viewmodel.MatchListViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailContract.ViewModelImpl as BottomSheetDetailViewModelImpl
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeContract.ViewModelImpl as HomeViewModelImpl
import com.example.mvvm_navigation.ui.main.matchlist.viewmodel.MatchListContract.ViewModelImpl as MatchListViewModelImpl

val mainModule = Kodein.Module(Contract.ModuleName.MAIN) {
    bind<HomeViewModelImpl>() with singleton {
        getMainViewModel(instance(HomeFragment().TAG))
    }
}

private fun getMainViewModel(fragment: HomeFragment): HomeViewModelImpl {
    val repository = Repository.getInstance(fragment.requireContext())
    val model = HomeModel.getInstance(repository)
    val factory = HomeViewModel.Factory(
        fragment.requireActivity().application,
        fragment.requireContext(),
        model,
        findNavController(fragment)
    )
    return ViewModelProvider(fragment, factory).get(HomeViewModel::class.java)
}

val thirdModule = Kodein.Module(Contract.ModuleName.THIRD) {
    bind<MatchListViewModelImpl>() with singleton {
        getMatchListViewModel(instance(MatchListFragment().TAG))
    }
}

private fun getMatchListViewModel(fragment: MatchListFragment): MatchListViewModelImpl {
    val repository = Repository.getInstance(fragment.requireContext())
    val model = MatchListModel.getInstance(repository)
    val factory = MatchListViewModel.Factory(
        fragment.requireActivity().application,
        fragment.requireContext(),
        model,
        findNavController(fragment)
    )
    return ViewModelProvider(fragment, factory).get(MatchListViewModel::class.java)
}

val bottomSheetDialogModule = Kodein.Module(Contract.ModuleName.BOTTOM_SHEET) {
    bind<BottomSheetDetailViewModelImpl>() with singleton {
        getBottomSheetDialogViewModel(instance(BottomSheetDetailFragment().TAG))
    }
}

private fun getBottomSheetDialogViewModel(fragment: BottomSheetDetailFragment): BottomSheetDetailViewModelImpl {
    val repository = Repository.getInstance(fragment.requireContext())
    val model = BottomSheetDetailModel.getInstance(repository)
    val factory = BottomSheetDetailViewModel.Factory(
        fragment.requireActivity().application,
        fragment.requireContext(),
        fragment,
        model,
        findNavController(fragment)
    )
    return ViewModelProvider(fragment, factory).get(BottomSheetDetailViewModel::class.java)
}