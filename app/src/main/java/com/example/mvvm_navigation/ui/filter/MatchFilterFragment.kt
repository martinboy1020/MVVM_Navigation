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
import com.example.mvvm_navigation.utils.LogUtils
import com.example.mvvm_navigation.widget.MatchFilterWidget
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
class MatchFilterFragment : BaseFragment(), MatchFilterWidget.MatchFilterWidgetOnChangeListener {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MatchFilterFragment>(MatchFilterFragment::class.java.simpleName) with singleton { this@MatchFilterFragment }
        import(matchFilterModule)
    }

    private val viewModel by kodein.instance<MatchFilterContract.ViewModelImpl>()
    private val selectedAreaList: MutableList<String> = mutableListOf()
    private val selectedCountryList: MutableList<String> = mutableListOf()
    private val selectedLeagueList: MutableList<Int> = mutableListOf()

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
        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetArea.setListener(this)
        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.setListener(this)
        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetLeague.setListener(this)
        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.visibility = View.GONE
        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetLeague.visibility = View.GONE
    }

    override fun changeStatus(id: Int, name: String, type: Int, isCheck: Boolean) {
        when (type) {
            MatchFilterWidget.FilterType.AREA.code -> {
                if (isCheck) selectedAreaList.add(name) else selectedAreaList.remove(name)
                (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.post {
                    (this.binding as FragmentMatchFilterBinding).matchFilterWidgetArea.showAttention(
                        selectedAreaList.size > 0
                    )
                    (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.showCountryRow(
                        id,
                        name,
                        isCheck
                    )
                    (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.visibility =
                        if (selectedAreaList.size > 0) View.VISIBLE else View.GONE
                    if (selectedAreaList.size <= 0) {
                        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.allUnSelected(false)
                        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetLeague.allUnSelected(false)
                    }
                }
            }
            MatchFilterWidget.FilterType.COUNTRY.code -> {
                if (isCheck) selectedCountryList.add(name) else selectedCountryList.remove(name)
                (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.post {
                    (this.binding as FragmentMatchFilterBinding).matchFilterWidgetCountry.showAttention(
                        selectedCountryList.size > 0
                    )
                    (this.binding as FragmentMatchFilterBinding).matchFilterWidgetLeague.showLeagueRow(
                        id,
                        name,
                        isCheck
                    )
                    (this.binding as FragmentMatchFilterBinding).matchFilterWidgetLeague.visibility =
                        if (selectedCountryList.size > 0) View.VISIBLE else View.GONE
                    if (selectedCountryList.size <= 0) {
                        (this.binding as FragmentMatchFilterBinding).matchFilterWidgetLeague.allUnSelected(false)
                    }
                }
            }
            MatchFilterWidget.FilterType.LEAGUE.code -> {
                LogUtils.d("tag12345", "MatchFilterFragment changeStatus name: $name, isCheck: $isCheck")
                if (isCheck) {
                    if (!selectedLeagueList.contains(id)) selectedLeagueList.add(id)
                } else {
                    if(selectedLeagueList.contains(id)) selectedLeagueList.remove(id)
                }
                LogUtils.d("tag12345", "selectedLeagueList size: ${selectedLeagueList.size}")
            }
        }
    }

}