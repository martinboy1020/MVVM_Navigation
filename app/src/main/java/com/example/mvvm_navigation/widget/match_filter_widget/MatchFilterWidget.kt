package com.example.mvvm_navigation.widget.match_filter_widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList

@BindingAdapter("areaList", "listener", requireAll = false)
fun setData(
    view: MatchFilterWidget,
    areaList: MutableList<MatchList.Area>?,
    listener: MatchFilterWidget.MatchFilterWidgetListener?
) {
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
                areaList[i].countries[j].areaName = areaList[i].name
                countryList.add(areaList[i].countries[j])
                for (k in areaList[i].countries[j].leagues.indices) {
                    areaList[i].countries[j].leagues[k].areaName = areaList[i].name
                    areaList[i].countries[j].leagues[k].countryName = areaList[i].countries[j].name
                    leagueList.add(areaList[i].countries[j].leagues[k])
                }
            }
        }
        matchFilterArea?.setData(areaList)
        matchFilterCountry?.setData(areaList)
        matchFilterLeague?.setData(areaList)
    }

    /**
     *  返回單點項目狀態
     */
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

                // 依據點選的洲別顯示該洲別的國家列
                matchFilterCountry?.showCountryRow(id, name, isCheck)
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

                // 依據點選的國家顯示該國家的賽事列
                matchFilterLeague?.showLeagueRow(id, name, isCheck)

                if (selectedCountryList.size <= 0) {
                    matchFilterCountry?.hideAllFilterRow(true)
                    matchFilterLeague?.hideAllFilterRow(false)
                }
            }

            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
                if (isCheck) {
                    if (!selectedLeagueList.contains(id)) selectedLeagueList.add(id)
                } else {
                    if (selectedLeagueList.contains(id)) selectedLeagueList.remove(id)
                }
            }
        }

        // 根據已選擇的洲別列表判斷是否要顯示警示
        matchFilterArea?.showAttention(selectedAreaList.size > 0)
        // 根據已選擇的國家列表判斷是否要顯示警示
        matchFilterCountry?.showAttention(selectedCountryList.size > 0)
        matchFilterCountry?.visibility =
            if (selectedAreaList.size > 0) View.VISIBLE else View.GONE
        matchFilterLeague?.visibility =
            if (selectedCountryList.size > 0) View.VISIBLE else View.GONE

        listener?.returnSelectedLeague(selectedLeagueList)
    }

    /**
     *  返回全選洲別項目狀態
     */
    override fun allSelectedFromAreaItemWidget(type: Int) {
        matchFilterArea?.showAttention(true)
        matchFilterArea?.showRows(true)
        matchFilterCountry?.visibility = View.VISIBLE
        for (i in areaList.indices) {
            if (!selectedAreaList.contains(areaList[i].name)) selectedAreaList.add(areaList[i].name)
            matchFilterCountry?.showCountryRow(areaList[i].id, areaList[i].name, true)
        }
    }

    /**
     *  返回全選國家項目狀態
     */
    override fun allSelectedFromCountryItemWidget(type: Int) {
        matchFilterCountry?.showAttention(true)
        matchFilterCountry?.showRows(false)
        matchFilterLeague?.visibility = View.VISIBLE
        for (i in countryList.indices) {
            if (selectedAreaList.contains(countryList[i].areaName) && !selectedCountryList.contains(countryList[i].name)) {
                selectedCountryList.add(
                    countryList[i].name
                )
                matchFilterLeague?.showLeagueRow(
                    countryList[i].id,
                    countryList[i].name,
                    true
                )
            }
        }
    }

    /**
     * 返回全選賽事項目狀態
     */
    override fun allSelectedFromLeagueItemWidget(type: Int) {
        matchFilterLeague?.showRows(false)
        for (i in leagueList.indices) {
            if(selectedCountryList.contains(leagueList[i].countryName) && !selectedLeagueList.contains(leagueList[i].id)) {
                selectedLeagueList.add(leagueList[i].id)
            }
        }
        listener?.returnSelectedLeague(selectedLeagueList)
    }

    /**
     * 返回反選項目狀態
     */
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
    }

    /**
     * 反選所有項目
     */
    fun allSelectedAllType() {
        matchFilterArea?.showAttention(true)
        matchFilterCountry?.showAttention(true)
        matchFilterCountry?.visibility = View.VISIBLE
        matchFilterLeague?.visibility = View.VISIBLE
        matchFilterArea?.showRows(true)
        matchFilterCountry?.showRows(true)
        matchFilterLeague?.showRows(true)
        for (i in areaList.indices) {
            if (!selectedAreaList.contains(areaList[i].name)) selectedAreaList.add(areaList[i].name)
        }
        for (i in countryList.indices) {
            if (!selectedCountryList.contains(countryList[i].name)) selectedCountryList.add(
                countryList[i].name
            )
        }
        for (i in leagueList.indices) {
            if (!selectedLeagueList.contains(leagueList[i].id)) selectedLeagueList.add(leagueList[i].id)
        }
        listener?.returnSelectedLeague(selectedLeagueList)
    }

    /**
     * 反選全項目
     */
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