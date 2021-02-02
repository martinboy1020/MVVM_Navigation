package com.example.mvvm_navigation.widget.match_filter_widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.utils.LogUtils

@BindingAdapter("areaList", "listener", requireAll = false)
fun setData(view: MatchFilterWidget, areaList: MutableList<MatchList.Area>?, listener: MatchFilterWidget.MatchFilterWidgetListener?) {
    if (areaList != null && areaList.size > 0) view.setData(areaList, listener)
}

// 賽事篩選Widget
class MatchFilterWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    MatchFilterItemWidget.MatchFilterWidgetOnChangeListener {

    private var attrs: AttributeSet? = null
    private var view: View? = null

    private var listener: MatchFilterWidgetListener? = null

    private var matchFilterArea: MatchFilterItemWidget? = null
    private var matchFilterCountry: MatchFilterItemWidget? = null
    private var matchFilterLeague: MatchFilterItemWidget? = null

    private var areaList: MutableList<MatchList.Area> = mutableListOf()
    private var countryList: MutableList<MatchList.Country> = mutableListOf()
    private var leagueList: MutableList<MatchList.Leagues> = mutableListOf()

    private var selectedAreaList: MutableList<String> = mutableListOf()
    private var selectedCountryList: MutableList<String> = mutableListOf()
    private var selectedLeagueList: MutableList<Int> = mutableListOf()

    init {
        this.attrs = attrs
        initView()
    }

    interface MatchFilterWidgetListener {
        fun returnSelectedLeague(selectedLeagueList: MutableList<Int>)
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_match_filter_widget, this)
        matchFilterArea = view?.findViewById(R.id.match_filter_item_widget_area)
        matchFilterCountry = view?.findViewById(R.id.match_filter_item_widget_country)
        matchFilterLeague = view?.findViewById(R.id.match_filter_item_widget_league)

        matchFilterArea?.setListener(this)
        matchFilterCountry?.setListener(this)
        matchFilterLeague?.setListener(this)

        matchFilterCountry?.visibility = View.GONE
        matchFilterLeague?.visibility = View.GONE
    }

    fun setData(areaList: MutableList<MatchList.Area>, listener: MatchFilterWidgetListener?) {
        this.areaList = areaList
        this.listener = listener
        for (i in areaList.indices) {
            for (j in areaList[i].countries.indices) {
                countryList.add(areaList[i].countries[j])
                for (k in areaList[i].countries[j].leagues.indices) {
                    leagueList.add(areaList[i].countries[j].leagues[k])
                }
            }
        }
        matchFilterArea?.setData(areaList)
        matchFilterCountry?.setData(areaList)
        matchFilterLeague?.setData(areaList)
    }

    override fun changeStatusFromItemWidget(id: Int, name: String, type: Int, isCheck: Boolean) {
        when (type) {
            MatchFilterItemWidget.FilterType.AREA.code -> {
                if (isCheck) {
                    selectedAreaList.add(name)
                } else {
                    selectedAreaList.remove(name)

                    var removeCountry = mutableListOf<MatchList.Country>()

                    for (i in areaList.indices) {
                        if (areaList[i].name == name) {
                            removeCountry = areaList[i].countries
                            break
                        }
                    }

                    for (i in removeCountry.indices) {
                        selectedCountryList.remove(removeCountry[i].name)
                        matchFilterLeague?.showLeagueRow(
                            removeCountry[i].id,
                            removeCountry[i].name,
                            false
                        )
                        for (j in removeCountry[i].leagues.indices) {
                            selectedLeagueList.remove(removeCountry[i].leagues[j].id)
                        }
                    }
                }

                matchFilterCountry?.showCountryRow(id, name, isCheck)
                matchFilterCountry?.visibility =
                    if (selectedAreaList.size > 0) View.VISIBLE else View.GONE

                if (selectedAreaList.size <= 0) {
                    matchFilterArea?.hideAllFilterRow(true)
                    matchFilterCountry?.hideAllFilterRow(false)
                    matchFilterLeague?.hideAllFilterRow(false)
                }
            }

            MatchFilterItemWidget.FilterType.COUNTRY.code -> {
                if (isCheck) {
                    selectedCountryList.add(name)
                } else {
                    selectedCountryList.remove(name)
                    var removeLeagues = mutableListOf<MatchList.Leagues>()
                    for (i in countryList.indices) {
                        if (countryList[i].name == name) {
                            removeLeagues = countryList[i].leagues
                            break
                        }
                    }
                    for (i in removeLeagues.indices) {
                        selectedLeagueList.remove(removeLeagues[i].id)
                    }
                }

                matchFilterCountry?.post {
                    matchFilterLeague?.showLeagueRow(id, name, isCheck)
                    if (selectedCountryList.size <= 0) {
                        matchFilterCountry?.hideAllFilterRow(true)
                        matchFilterLeague?.hideAllFilterRow(false)
                    }
                }
            }

            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
                if (isCheck) {
                    if (!selectedLeagueList.contains(id)) selectedLeagueList.add(id)
                } else {
                    if (selectedLeagueList.contains(id)) selectedLeagueList.remove(id)
                }
                listener?.returnSelectedLeague(selectedLeagueList)
            }
        }

        matchFilterArea?.showAttention(selectedAreaList.size > 0)
        matchFilterCountry?.showAttention(selectedCountryList.size > 0)
        matchFilterLeague?.visibility =
            if (selectedCountryList.size > 0) View.VISIBLE else View.GONE


//        LogUtils.d(
//            "tag123456789",
//            "changeStatusFromItemWidget area selectedAreaList size: " + selectedAreaList.size
//        )
//
//        LogUtils.d(
//            "tag123456789",
//            "changeStatusFromItemWidget area selectedCountryList size: " + selectedCountryList.size
//        )
//
//        LogUtils.d(
//            "tag123456789",
//            "changeStatusFromItemWidget Selected League Size: " + selectedLeagueList.size
//        )

    }

    override fun allSelectedFromAreaItemWidget(type: Int) {
        matchFilterArea?.showAttention(true)
        matchFilterArea?.allSelected()
        matchFilterCountry?.visibility = View.VISIBLE
        for (i in areaList.indices) {
            selectedAreaList.add(areaList[i].name)
            matchFilterCountry?.showCountryRow(areaList[i].id, areaList[i].name, true)
        }
    }

    override fun allSelectedFromCountryItemWidget(type: Int) {
        matchFilterCountry?.showAttention(true)
        matchFilterCountry?.allSelected()
        matchFilterLeague?.visibility = View.VISIBLE
        for (i in countryList.indices) {
            selectedCountryList.add(countryList[i].name)
            matchFilterLeague?.showLeagueRow(countryList[i].id, countryList[i].name, true)
        }
    }

    override fun allSelectedFromLeagueItemWidget(type: Int) {
        matchFilterLeague?.allSelected()
        for (i in leagueList.indices) {
            selectedLeagueList.add(leagueList[i].id)
        }

        listener?.returnSelectedLeague(selectedLeagueList)

//        LogUtils.d(
//            "tag123456789",
//            "allSelectedFromLeagueItemWidget Selected League size: " + selectedLeagueList.size
//        )

    }

    override fun allUnSelectedFromItemWidget(type: Int) {
        when (type) {
            MatchFilterItemWidget.FilterType.AREA.code -> {
                selectedAreaList.removeAll { true }
                selectedCountryList.removeAll { true }
                selectedLeagueList.removeAll { true }
                matchFilterArea?.showAttention(false)
                matchFilterCountry?.showAttention(false)
                matchFilterArea?.hideAllFilterRow(true)
                matchFilterCountry?.hideAllFilterRow(false)
                matchFilterLeague?.hideAllFilterRow(false)
                matchFilterCountry?.visibility = View.GONE
                matchFilterLeague?.visibility = View.GONE
            }

            MatchFilterItemWidget.FilterType.COUNTRY.code -> {
                selectedCountryList.removeAll { true }
                selectedLeagueList.removeAll { true }
                matchFilterCountry?.showAttention(false)
                matchFilterCountry?.hideAllFilterRow(true)
                matchFilterLeague?.hideAllFilterRow(false)
                matchFilterLeague?.visibility = View.GONE
            }

            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
                selectedLeagueList.removeAll { true }
                matchFilterLeague?.hideAllFilterRow(true)
            }
        }

        listener?.returnSelectedLeague(selectedLeagueList)

//        LogUtils.d(
//            "tag123456789",
//            "allUnSelectedFromItemWidget Selected League size: " + selectedLeagueList.size
//        )

    }

    fun clearAllSelected() {
        selectedAreaList.removeAll { true }
        selectedCountryList.removeAll { true }
        selectedLeagueList.removeAll { true }
        matchFilterArea?.showAttention(false)
        matchFilterCountry?.showAttention(false)
        matchFilterArea?.hideAllFilterRow(true)
        matchFilterCountry?.hideAllFilterRow(false)
        matchFilterLeague?.hideAllFilterRow(false)
        matchFilterCountry?.visibility = View.GONE
        matchFilterLeague?.visibility = View.GONE
        listener?.returnSelectedLeague(selectedLeagueList)
    }

}