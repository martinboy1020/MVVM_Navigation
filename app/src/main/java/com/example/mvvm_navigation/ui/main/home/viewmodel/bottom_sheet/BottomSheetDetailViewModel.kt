package com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseViewModel
import com.example.mvvm_navigation.widget.ItemMatchSelectorWidget

class BottomSheetDetailViewModel constructor(
    application: Application,
    context: Context,
    private val listener: BottomSheetDetailViewModelImpl,
    val model: BottomSheetDetailContract.ModelImpl,
    navController: NavController
) : BaseViewModel(application, context, navController), BottomSheetDetailContract.ViewModelImpl,
    View.OnClickListener, ItemMatchSelectorWidget.CheckBoxListener,
    RadioGroup.OnCheckedChangeListener {

    private val submitter =
        BottomSheetDetailFragmentSubmitter()

    init {
        this.submitter.onClickListener.value = this
        this.submitter.betList.value = model.getBetList()
        this.submitter.recentMatchConditionList.value = model.getRecentMatchCondition()
        this.submitter.homeAwayFilterListener.value = this
        this.submitter.checkBoxCheckedListener.value = this
    }

    interface BottomSheetDetailViewModelImpl {
        fun closeDialog()
    }

    companion object {
        fun getInstance(
            application: Application,
            context: Context,
            listener: BottomSheetDetailViewModelImpl,
            model: BottomSheetDetailContract.ModelImpl,
            navController: NavController
        ) =
            BottomSheetDetailViewModel(
                application,
                context,
                listener,
                model,
                navController
            )
    }

    override fun getSubmitter(): BottomSheetDetailFragmentSubmitter = this.submitter

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_close -> {
                listener.closeDialog()
            }
        }
    }

    class Factory(
        val application: Application,
        val context: Context,
        val listener: BottomSheetDetailViewModelImpl,
        val model: BottomSheetDetailContract.ModelImpl,
        val navController: NavController
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = getInstance(
            application,
            context,
            listener,
            model,
            navController
        ) as T
    }

    override fun getFilterType(type: Int, isChecked: Boolean) {
        when (type) {
            ItemMatchSelectorWidget.FilterType.MATCH.type -> {
                Toast.makeText(this.context, "Match Filter is $isChecked", Toast.LENGTH_SHORT)
                    .show()
            }
            ItemMatchSelectorWidget.FilterType.HOME.type -> {
                Toast.makeText(this.context, "Home Filter is $isChecked", Toast.LENGTH_SHORT).show()
            }
            ItemMatchSelectorWidget.FilterType.AWAY.type -> {
                Toast.makeText(this.context, "Away Filter is $isChecked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val radBtn = p0?.findViewById<RadioButton>(p1)
        Toast.makeText(this.context, "Filter is ${radBtn?.text}", Toast.LENGTH_SHORT).show()
    }
}