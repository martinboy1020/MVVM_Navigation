package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.filter.MatchFilterAdapter

@BindingAdapter("areaList", requireAll = false)
fun setData(view: MatchFilterWidget, areaList: MutableList<MatchList.Area>?) {
    if (areaList != null && areaList.size > 0) {
        view.setData(areaList)
    }
}

class MatchFilterWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var attrs: AttributeSet? = null
    private var view: View? = null
    private var matchFilterRow: LinearLayout? = null
    private var tvMatchFilterWarning: TextView? = null
    private var tvMatchFilterWidgetTitle: TextView? = null
    var dataType: Int? = null

    enum class FilterType(val code: Int) {
        AREA(0), COUNTRY(1), LEAGUE(2)
    }

    init {
        this.attrs = attrs
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_match_filter_widget, this)
        matchFilterRow = view?.findViewById(R.id.match_filter_row)
        tvMatchFilterWarning = view?.findViewById(R.id.tv_match_filter_warning)
        tvMatchFilterWidgetTitle = view?.findViewById(R.id.tv_match_filter_widget_title)
        val typedArray =
            this.context.obtainStyledAttributes(this.attrs, R.styleable.MatchFilterWidget)
        dataType = typedArray.getInt(R.styleable.MatchFilterWidget_dataType, 0)
        typedArray.recycle()

        when (dataType) {

            FilterType.AREA.code -> {
                tvMatchFilterWarning?.text = "請選擇洲別"
                tvMatchFilterWidgetTitle?.text = "洲別"
            }

            FilterType.COUNTRY.code -> {
                tvMatchFilterWarning?.text = "請選擇國家"
                tvMatchFilterWidgetTitle?.text = "國家"
            }

            FilterType.LEAGUE.code -> {
                tvMatchFilterWarning?.text = "請選擇賽事"
                tvMatchFilterWarning?.visibility = View.GONE
                tvMatchFilterWidgetTitle?.text = "賽事"
            }

        }
    }

    fun setData(areaList: MutableList<MatchList.Area>) {
        when(dataType) {
            FilterType.AREA.code -> {
                setAreaData(areaList)
            }
            FilterType.COUNTRY.code -> {
                setCountryData(areaList)
            }
            FilterType.LEAGUE.code -> {
                setLeagueData(areaList)
            }
        }
    }

    private fun setAreaData(areaList: MutableList<MatchList.Area>) {
        val widget = MatchFilterItemWidget(this.context)
        widget.setAreaData(areaList, null)
        matchFilterRow?.addView(widget)
    }

    private fun setCountryData(areaList: MutableList<MatchList.Area>) {
        for(i in areaList.indices) {
            val widget = MatchFilterItemWidget(this.context)
            widget.setCountryData(areaList[i].countries, areaList[i].id, areaList[i].name)
            matchFilterRow?.addView(widget)
        }
    }

    private fun setLeagueData(areaList: MutableList<MatchList.Area>) {
        for(i in areaList.indices) {
            val countryList = areaList[i].countries
            for(j in countryList.indices) {
                val widget = MatchFilterItemWidget(this.context)
                widget.setLeagueData(countryList[j].leagues, countryList[j].id, countryList[j].name)
                matchFilterRow?.addView(widget)
            }
        }
    }

}