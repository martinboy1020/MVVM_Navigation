package com.example.mvvm_navigation.widget.match_filter_widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.utils.LogUtils

@BindingAdapter("areaList", requireAll = false)
fun setData(view: MatchFilterWidget, areaList: MutableList<MatchList.Area>?) {
    if (areaList != null && areaList.size > 0) view.setData(areaList)
}

// 賽事篩選Widget
class MatchFilterWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener, MatchFilterItemWidget.MatchFilterWidgetOnChangeListener {

    private var attrs: AttributeSet? = null
    private var view: View? = null

    private var matchFilterArea: MatchFilterItemWidget? = null
    private var matchFilterCountry: MatchFilterItemWidget? = null
    private var matchFilterLeague: MatchFilterItemWidget? = null

    private var allAreaList: MutableList<String> = mutableListOf()
    private var allCountryList: MutableList<String> = mutableListOf()
    private var allLeagueList: MutableList<String> = mutableListOf()

    private var selectedAreaList: MutableList<String> = mutableListOf()
    private var selectedCountryList: MutableList<String> = mutableListOf()
    private var selectedLeagueList: MutableList<Int> = mutableListOf()

    init {
        this.attrs = attrs
        initView()
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

    fun setData(areaList: MutableList<MatchList.Area>) {
        matchFilterArea?.setData(areaList)
        matchFilterCountry?.setData(areaList)
        matchFilterLeague?.setData(areaList)
    }

    override fun onClick(p0: View?) {

    }

    override fun changeStatusFromItemWidget(id: Int, name: String, type: Int, isCheck: Boolean) {
        when (type) {
            MatchFilterItemWidget.FilterType.AREA.code -> {
                LogUtils.d("tag123456789", "changeStatusFromItemWidget area")
                if (isCheck) selectedAreaList.add(name) else selectedAreaList.remove(name)
                matchFilterCountry?.post {
                    matchFilterArea?.showAttention(selectedAreaList.size > 0)
                    matchFilterCountry?.showCountryRow(id, name, isCheck)
                    matchFilterCountry?.visibility = if (selectedAreaList.size > 0) View.VISIBLE else View.GONE

                    if(selectedAreaList.size <= 0) {
                        matchFilterArea?.hideAllFilterRow(true)
                        matchFilterCountry?.hideAllFilterRow(false)
                        matchFilterLeague?.hideAllFilterRow(false)
                    }

//                    if (selectedAreaList.size <= 0) {
//                        matchFilterCountry?.allUnSelected(false)
//                        matchFilterLeague?.allUnSelected(false)
//                    }
                }
            }
            MatchFilterItemWidget.FilterType.COUNTRY.code -> {
                if (isCheck) selectedCountryList.add(name) else selectedCountryList.remove(name)
                matchFilterCountry?.post {
                    matchFilterCountry?.showAttention(selectedCountryList.size > 0)
                    matchFilterLeague?.showLeagueRow(id, name, isCheck)
                    matchFilterLeague?.visibility = if (selectedCountryList.size > 0) View.VISIBLE else View.GONE

                    if(selectedCountryList.size <= 0) {
                        matchFilterCountry?.hideAllFilterRow(true)
                        matchFilterLeague?.hideAllFilterRow(false)
                    }
                }
            }
            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
//                LogUtils.d(
//                    "tag12345",
//                    "MatchFilterFragment changeStatus name: $name, isCheck: $isCheck"
//                )
                if (isCheck) {
                    if (!selectedLeagueList.contains(id)) selectedLeagueList.add(id)
                } else {
                    if (selectedLeagueList.contains(id)) selectedLeagueList.remove(id)
                }

                if(selectedLeagueList.size <= 0) {
                    matchFilterLeague?.hideAllFilterRow(false)
                }

//                LogUtils.d(
//                    "tag12345",
//                    "selectedLeagueList size: ${selectedLeagueList.size}"
//                )
            }
        }

        if(selectedLeagueList.size == 0) {
            LogUtils.d("tag123456789", "changeStatusFromItemWidget Not Selected Any League")
        } else {
            for(i in selectedLeagueList.indices) {
                LogUtils.d("tag123456789", "changeStatusFromItemWidget Selected League Id: " + selectedLeagueList[i])
            }
        }

    }

    override fun allSelectedFromItemWidget(type: Int) {
        when(type) {
            MatchFilterItemWidget.FilterType.AREA.code -> {
                LogUtils.d("tag123456789", "allSelectedFromItemWidget area")
                matchFilterArea?.showAttention(true)
                matchFilterArea?.showAllFilterRow(true)
                matchFilterCountry?.showAllFilterRow(false)
                matchFilterLeague?.showAllFilterRow(false)
                matchFilterCountry?.visibility = View.GONE
                matchFilterLeague?.visibility = View.GONE
            }

            MatchFilterItemWidget.FilterType.COUNTRY.code -> {
                LogUtils.d("tag123456789", "allSelectedFromItemWidget country")
                matchFilterCountry?.showAttention(true)
                matchFilterCountry?.showAllFilterRow(true)
                matchFilterLeague?.showAllFilterRow(false)
                matchFilterLeague?.visibility = View.GONE
            }

            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
                LogUtils.d("tag123456789", "allSelectedFromItemWidget league")
                matchFilterLeague?.showAllFilterRow(true)
            }
        }
    }

    override fun allUnSelectedFromItemWidget(type: Int) {
        when(type) {
            MatchFilterItemWidget.FilterType.AREA.code -> {
                LogUtils.d("tag123456789", "allUnSelectedFromItemWidget area")
                selectedAreaList.removeAll { true }
                selectedCountryList.removeAll { true }
                selectedLeagueList.removeAll { true }
                matchFilterArea?.showAttention(false)
                matchFilterArea?.hideAllFilterRow(true)
                matchFilterCountry?.hideAllFilterRow(false)
                matchFilterLeague?.hideAllFilterRow(false)
                matchFilterCountry?.visibility = View.GONE
                matchFilterLeague?.visibility = View.GONE
            }

            MatchFilterItemWidget.FilterType.COUNTRY.code -> {
                LogUtils.d("tag123456789", "allUnSelectedFromItemWidget country")
                selectedCountryList.removeAll { true }
                selectedLeagueList.removeAll { true }
                matchFilterCountry?.showAttention(false)
                matchFilterCountry?.hideAllFilterRow(true)
                matchFilterLeague?.hideAllFilterRow(false)
                matchFilterLeague?.visibility = View.GONE
            }

            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
                LogUtils.d("tag123456789", "allUnSelectedFromItemWidget league")
                selectedLeagueList.removeAll { true }
                matchFilterLeague?.hideAllFilterRow(true)
            }
        }

        if(selectedLeagueList.size == 0) {
            LogUtils.d("tag123456789", "allUnSelectedFromItemWidget Not Selected Any League")
        } else {
            for(i in selectedLeagueList.indices) {
                LogUtils.d("tag123456789", "allUnSelectedFromItemWidget Selected League Id: " + selectedLeagueList[i])
            }
        }


    }
}