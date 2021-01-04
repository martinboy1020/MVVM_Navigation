package com.example.mvvm_navigation.ui.main.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.LeagueTeamData
import com.example.mvvm_navigation.di.bottomSheetDialogModule
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailContract
import com.example.mvvm_navigation.ui.main.home.viewmodel.bottom_sheet.BottomSheetDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_detail.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton


class BottomSheetDetailFragment : BottomSheetDialogFragment(), KodeinAware,
    BottomSheetDetailViewModel.BottomSheetDetailViewModelImpl {

    private var taskHandler = Handler(Looper.getMainLooper())
    private var runnable =
        Runnable { dialog?.window?.setWindowAnimations(R.style.Animation_Design_BottomSheetDialog) }

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<BottomSheetDetailFragment>(BottomSheetDetailFragment::class.java.simpleName) with singleton { this@BottomSheetDetailFragment }
        import(bottomSheetDialogModule)
    }

    private val viewModel by kodein.instance<BottomSheetDetailContract.ViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogRoundCorner)
        val leagueTeamData = arguments?.let {
            BottomSheetDetailFragmentArgs.fromBundle(
                it
            ).leagueTeamData
        }
        initDataAndButton(leagueTeamData)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val height = (resources.displayMetrics.heightPixels * 0.95).toInt()
        return FixedHeightBottomSheetDialog(
            this.context,
            theme,
            height
        )
    }

    override fun onResume() {
        super.onResume()
        taskHandler.postDelayed(runnable, 500)
    }

    override fun onPause() {
        super.onPause()
        dialog?.window?.setWindowAnimations(R.style.bottomSheetAnimation)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_bottom_sheet_detail,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        dialog?.window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        val spinner = binding.root.spinner_match_filter
        spinner.setSelection(1)
        return binding.root
    }

    private fun initDataAndButton(leagueTeamData: LeagueTeamData?) {
        this.viewModel.getSubmitter().leagueTeamData.value = leagueTeamData
        val matchStatisticsValue = BottomSheetDetailViewModel.MatchStatisticsValue()
        matchStatisticsValue.leagueId = leagueTeamData?.leagueId
        this.viewModel.getSubmitter().matchStatisticsValue.value = matchStatisticsValue
        this.viewModel.getSubmitter().radioBtnEnable.value =
            !((matchStatisticsValue.homeId != null && matchStatisticsValue.awayId != null) ||
                    (matchStatisticsValue.homeId == null && matchStatisticsValue.awayId == null))
        this.viewModel.getMatchStatistics(matchStatisticsValue)
    }

    override fun closeDialog() {
        dialog?.dismiss()
    }
}

class FixedHeightBottomSheetDialog(
    context: Context?,
    theme: Int,
    private val fixedHeight: Int
) : BottomSheetDialog(context!!, theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPeekHeight(fixedHeight)
        setMaxHeight(fixedHeight)
    }

    private fun setPeekHeight(peekHeight: Int) {
        if (peekHeight <= 0) {
            return
        }
        val bottomSheetBehavior = getBottomSheetBehavior()
        bottomSheetBehavior?.peekHeight = peekHeight // 設定BottomSheetFragment彈起時的高度
        bottomSheetBehavior?.isHideable = false // 設定BottomSheetFragment不會被下拉收回
    }

    private fun setMaxHeight(maxHeight: Int) {
        if (maxHeight <= 0) {
            return
        }
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight)
        window?.setGravity(Gravity.BOTTOM)
    }

    private fun getBottomSheetBehavior(): BottomSheetBehavior<View>? {
        val view: View? =
            window?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        return view?.let { BottomSheetBehavior.from(view) }
    }
}