package com.example.mvvm_navigation.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.databinding.FragmentIncorrectScoreFilterBinding
import com.example.mvvm_navigation.di.incorrectScoreFilterModule
import com.example.mvvm_navigation.ui.filter.viewmodel.incorrect_score_filter.IncorrectScoreFilterContract
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_incorrect_score_filter)
class IncorrectScoreFilterFragment : BaseFragment(), RadioGroup.OnCheckedChangeListener,
    OnSeekChangeListener {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<IncorrectScoreFilterFragment>(IncorrectScoreFilterFragment::class.java.simpleName) with singleton { this@IncorrectScoreFilterFragment }
        import(incorrectScoreFilterModule)
    }

    private val viewModel by kodein.instance<IncorrectScoreFilterContract.ViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        this.viewModel.getSubmitter().onCheckedChangeListener.value = this
        (this.binding as FragmentIncorrectScoreFilterBinding).sbAppearRate.onSeekChangeListener =
            this
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val radBtn = p0?.findViewById<RadioButton>(p1)
        when (radBtn?.id) {
            R.id.rdb_appear_rate -> {
                this.viewModel.getSubmitter().sbAppearRateVisible.value = View.VISIBLE
                this.viewModel.getSubmitter().layoutContinueMatchVisible.value = View.GONE
            }
            R.id.rdb_continue_match -> {
                this.viewModel.getSubmitter().sbAppearRateVisible.value = View.GONE
                this.viewModel.getSubmitter().layoutContinueMatchVisible.value = View.VISIBLE
            }
        }
    }

    override fun onSeeking(seekParams: SeekParams?) {
        Log.d(
            "tag123456789",
            "onSeeking seekParams?.fromUser " + seekParams?.fromUser + " now seek count equal " + seekParams?.progress
        )
    }

    override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {}
    override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
        Log.d(
            "tag123456789",
            "onStopTrackingTouch seekBar?.progress: " + seekBar?.progress
        )
    }

}

