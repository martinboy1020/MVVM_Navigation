package com.example.mvvm_navigation.di

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.ui.filter.IncorrectScoreFilterFragment
import com.example.mvvm_navigation.ui.filter.MatchFilterFragment
import com.example.mvvm_navigation.ui.filter.model.IncorrectScoreFilterModel
import com.example.mvvm_navigation.ui.filter.model.MatchFilterModel
import com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter.IncorrectScoreFilterViewModel
import com.example.mvvm_navigation.ui.filter.viewmodel.match_filter.MatchFilterViewModel
import com.example.mvvm_navigation.ui.main.home.BottomSheetDetailFragment
import com.example.mvvm_navigation.ui.main.home.HomeFragment
import com.example.mvvm_navigation.ui.main.home.model.BottomSheetDetailModel
import com.example.mvvm_navigation.ui.main.home.model.HomeModel
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailViewModel
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeViewModel
import com.example.mvvm_navigation.ui.match.matchlist.MatchListFragment
import com.example.mvvm_navigation.ui.match.matchlist.model.MatchListModel
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.lang.Exception
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailContract.ViewModelImpl as BottomSheetDetailViewModelImpl
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeContract.ViewModelImpl as HomeViewModelImpl
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListContract.ViewModelImpl as MatchListViewModelImpl
import com.example.mvvm_navigation.ui.filter.viewmodel.match_filter.MatchFilterContract.ViewModelImpl as MatchFilterViewModelImpl
import com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter.IncorrectScoreFilterContract.ViewModelImpl as InCorrectScoreViewModelImpl

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

val matchListModule = Kodein.Module(Contract.ModuleName.MATCH_LIST) {
    bind<MatchListViewModelImpl>() with singleton {
        getMatchListViewModel(instance(MatchListFragment().javaClass.simpleName))
    }
}

private fun getMatchListViewModel(fragment: MatchListFragment): MatchListViewModelImpl {
    val repository = Repository.getInstance(fragment.requireContext())
    val model = MatchListModel.getInstance(repository)
    var findNavController: NavController? = null
    try {
        findNavController = findNavController(fragment)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val factory = MatchListViewModel.Factory(
        fragment.requireActivity().application,
        fragment.requireContext(),
        model,
        findNavController
    )
    return ViewModelProvider(fragment, factory).get(MatchListViewModel::class.java)
}

val bottomSheetDialogModule = Kodein.Module(Contract.ModuleName.BOTTOM_SHEET) {
    bind<BottomSheetDetailViewModelImpl>() with singleton {
        getBottomSheetDialogViewModel(instance(BottomSheetDetailFragment().javaClass.simpleName))
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

val matchFilterModule = Kodein.Module(Contract.ModuleName.MATCH_FILTER) {
    bind<MatchFilterViewModelImpl>() with singleton {
        getMatchFilterViewModel(instance(MatchFilterFragment().javaClass.simpleName))
    }
}

private fun getMatchFilterViewModel(fragment: MatchListFragment): MatchFilterViewModelImpl {
    val repository = Repository.getInstance(fragment.requireContext())
    val model = MatchFilterModel.getInstance(repository)
    var findNavController: NavController? = null
    try {
        findNavController = findNavController(fragment)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val factory = MatchFilterViewModel.Factory(
        fragment.requireActivity().application,
        fragment.requireContext(),
        model,
        findNavController
    )
    return ViewModelProvider(fragment, factory).get(MatchFilterViewModel::class.java)
}

val incorrectScoreFilterModule = Kodein.Module(Contract.ModuleName.INCORRECT_SCORE_FILTER) {
    bind<InCorrectScoreViewModelImpl>() with singleton {
        getIncorrectScoreFilterViewModel(instance(IncorrectScoreFilterFragment().javaClass.simpleName))
    }
}

private fun getIncorrectScoreFilterViewModel(fragment: IncorrectScoreFilterFragment): InCorrectScoreViewModelImpl {
    val repository = Repository.getInstance(fragment.requireContext())
    val model = IncorrectScoreFilterModel.getInstance(repository)
    var findNavController: NavController? = null
    try {
        findNavController = findNavController(fragment)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val factory = IncorrectScoreFilterViewModel.Factory(
        fragment.requireActivity().application,
        fragment.requireContext(),
        model,
        findNavController
    )
    return ViewModelProvider(fragment, factory).get(IncorrectScoreFilterViewModel::class.java)
}