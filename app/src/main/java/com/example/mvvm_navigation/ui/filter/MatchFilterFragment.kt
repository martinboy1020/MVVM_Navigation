package com.example.mvvm_navigation.ui.filter

import android.os.Bundle
import android.view.View
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.databinding.FragmentMatchFilterBinding
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.di.matchFilterModule
import com.example.mvvm_navigation.ui.filter.viewmodel.match_filter.MatchFilterContract
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton
import java.lang.reflect.Type

@LayoutId(R.layout.fragment_match_filter)
class MatchFilterFragment : BaseFragment(), View.OnClickListener {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MatchFilterFragment>(MatchFilterFragment::class.java.simpleName) with singleton { this@MatchFilterFragment }
        import(matchFilterModule)
    }

    private val viewModel by kodein.instance<MatchFilterContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        val bundle = this.arguments
        val listString = bundle?.getString("filter", "")
        if (!listString.isNullOrEmpty()) {
            val type: Type = object :
                TypeToken<MutableList<MatchList.Area>?>() {}.type
            val areaList = Gson().fromJson<MutableList<MatchList.Area>>(listString, type)
            if (!areaList.isNullOrEmpty()) this.viewModel.setMatchFilter(areaList)
        }
        (this.binding as FragmentMatchFilterBinding).btnAllSelectedAllType.setOnClickListener(this)
        (this.binding as FragmentMatchFilterBinding).btnAllUnselectedAllType.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_all_selected_all_type -> (this.binding as FragmentMatchFilterBinding).matchFilterWidget.allSelectedAllType()
            R.id.btn_all_unselected_all_type -> (this.binding as FragmentMatchFilterBinding).matchFilterWidget.clearAllSelected()
        }
    }

}