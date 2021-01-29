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
                if (isCheck) selectedAreaList.add(name) else selectedAreaList.remove(name)
                matchFilterCountry?.post {
                    matchFilterArea?.showAttention(selectedAreaList.size > 0)
                    matchFilterCountry?.showCountryRow(id, name, isCheck)
                    matchFilterCountry?.visibility = if (selectedAreaList.size > 0) View.VISIBLE else View.GONE
                    if (selectedAreaList.size <= 0) {
                        matchFilterCountry?.allUnSelected(false)
                        matchFilterLeague?.allUnSelected(false)
                    }
                }
            }
            MatchFilterItemWidget.FilterType.COUNTRY.code -> {
                if (isCheck) selectedCountryList.add(name) else selectedCountryList.remove(name)
                matchFilterCountry?.post {
                    matchFilterCountry?.showAttention(selectedCountryList.size > 0)
                    matchFilterLeague?.showLeagueRow(id, name, isCheck)
                    matchFilterLeague?.visibility = if (selectedCountryList.size > 0) View.VISIBLE else View.GONE
                    if (selectedCountryList.size <= 0) {
                        matchFilterLeague?.allUnSelected(false)
                    }
                }
            }
            MatchFilterItemWidget.FilterType.LEAGUE.code -> {
                LogUtils.d(
                    "tag12345",
                    "MatchFilterFragment changeStatus name: $name, isCheck: $isCheck"
                )
                if (isCheck) {
                    if (!selectedLeagueList.contains(id)) selectedLeagueList.add(id)
                } else {
                    if (selectedLeagueList.contains(id)) selectedLeagueList.remove(id)
                }
                LogUtils.d(
                    "tag12345",
                    "selectedLeagueList size: ${selectedLeagueList.size}"
                )
            }
        }
    }
}