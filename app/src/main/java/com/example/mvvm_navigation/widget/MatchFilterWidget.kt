package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList

@BindingAdapter("areaList", requireAll = false)
fun setData(view: MatchFilterWidget, areaList: MutableList<MatchList.Area>?) {
    if (areaList != null && areaList.size > 0) {
        view.setData(areaList)
    }
}

// 賽事篩選Widget
class MatchFilterWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener,
    MatchFilterItemWidget.MatchFilterItemWidgetOnChangeListener {

    private var attrs: AttributeSet? = null
    private var view: View? = null
    private var matchFilterRow: LinearLayout? = null
    private var tvMatchFilterWarning: TextView? = null
    private var tvMatchFilterWidgetTitle: TextView? = null
    private var btnAllSelected: Button? = null
    private var btnAllUnSelected: Button? = null
    private var countryListIndex: MutableMap<String, Int> = mutableMapOf()
    private var leagueListIndex: MutableMap<String, Int> = mutableMapOf()
    private var listener: MatchFilterWidgetOnChangeListener? = null
    private var dataType: Int = -1

    enum class FilterType(val code: Int) {
        AREA(0), COUNTRY(1), LEAGUE(2)
    }

    interface MatchFilterWidgetOnChangeListener {
        fun changeStatus(id: Int, name: String, type: Int, isCheck: Boolean)
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
        btnAllSelected = view?.findViewById(R.id.btn_all_selected)
        btnAllUnSelected = view?.findViewById(R.id.btn_all_unselected)
        btnAllSelected?.setOnClickListener(this)
        btnAllUnSelected?.setOnClickListener(this)
        val typedArray =
            this.context.obtainStyledAttributes(this.attrs, R.styleable.MatchFilterWidget)
        dataType = typedArray.getInt(R.styleable.MatchFilterWidget_dataType, -1)
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
        when (dataType) {
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
        widget.setListener(this)
        matchFilterRow?.addView(widget)
    }

    private fun setCountryData(areaList: MutableList<MatchList.Area>) {
        for (i in areaList.indices) {
            val widget = MatchFilterItemWidget(this.context)
            countryListIndex[areaList[i].name] = i
            widget.setCountryData(areaList[i].countries, areaList[i].id, areaList[i].name)
            widget.setListener(this)
            widget.visibility = View.GONE
            matchFilterRow?.addView(widget)
        }
    }

    private fun setLeagueData(areaList: MutableList<MatchList.Area>) {
        var index = 0
        for (i in areaList.indices) {
            val countryList = areaList[i].countries
            for (j in countryList.indices) {
                val widget = MatchFilterItemWidget(this.context)
                leagueListIndex[countryList[j].name] = index++
                widget.setLeagueData(countryList[j].leagues, countryList[j].id, countryList[j].name)
                widget.setListener(this)
                widget.visibility = View.GONE
                matchFilterRow?.addView(widget)
            }
        }
    }

    fun showAttention(isExist: Boolean) {
        this.post { tvMatchFilterWarning?.visibility = if(isExist) View.GONE else View.VISIBLE }
    }

    fun showCountryRow(id: Int, name: String, isCheck: Boolean) {
        val position = countryListIndex[name]
        val widget = matchFilterRow!![position!!] as MatchFilterItemWidget
        widget.visibility = if (isCheck) View.VISIBLE else View.GONE
        if(!isCheck) allUnSelected(false)
    }

    fun showLeagueRow(id: Int, name: String, isCheck: Boolean) {
        val position = leagueListIndex[name]
        val widget = matchFilterRow!![position!!] as MatchFilterItemWidget
        widget.visibility = if (isCheck) View.VISIBLE else View.GONE
        if(!isCheck) allUnSelected(false)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_all_selected -> {
                allSelected(false)
            }
            R.id.btn_all_unselected -> {
                allUnSelected(true)
            }
        }
    }

    fun allSelected(isFromOutSideButton: Boolean) {
        if (matchFilterRow != null) {
            for (i in 0 until matchFilterRow!!.childCount) {
                val widget = matchFilterRow!![i] as MatchFilterItemWidget
                if(isFromOutSideButton) {
                    widget.allSelected()
                } else {
                    if(widget.visibility == View.VISIBLE) widget.allSelected()
                }
            }
        }
    }

    fun allUnSelected(clickByWidgetButton: Boolean) {
        for (i in 0 until matchFilterRow!!.childCount) {
            val widget = matchFilterRow!![i] as MatchFilterItemWidget
            if(clickByWidgetButton) {
                widget.allUnselected()
            } else {
                if(widget.visibility == View.GONE) widget.allUnselected()
            }
        }
    }

    fun setListener(mMatchFilterItemWidgetOnChangeListener: MatchFilterWidgetOnChangeListener) {
        listener = mMatchFilterItemWidgetOnChangeListener
    }

    override fun changeStatus(id: Int, name: String, isCheck: Boolean) {
        listener?.changeStatus(id, name, dataType, isCheck)
    }

}