package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.filter.MatchFilterAdapter

// 賽事篩選Row(各個洲別, 國家, 賽事)
class MatchFilterItemWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    MatchFilterAdapter.MatchFilterAdapterOnChangeListener {

    private var attrs: AttributeSet? = null
    private var view: View? = null
    private var rvMatchItemFilter: BuildRecyclerView? = null
    private var adapter: MatchFilterAdapter? = null
    private var tvMatchFilterItemTitle: TextView? = null
    private var listener: MatchFilterItemWidgetOnChangeListener? = null
    var dataType: Int? = null
    var id: Int? = null

    enum class DataType(val code: Int) {
        AREA(0), COUNTRY(1), LEAGUE(2)
    }

    interface MatchFilterItemWidgetOnChangeListener {
        fun changeStatus(id: Int, name: String, isCheck: Boolean)
    }

    init {
        this.attrs = attrs
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_match_filter_item_widget, this)
        rvMatchItemFilter = view?.findViewById(R.id.rv_match_item_filter)
        tvMatchFilterItemTitle = view?.findViewById(R.id.tv_match_filter_item_title)
    }

    fun setAreaData(areaList: MutableList<MatchList.Area>, title: String?) {
        this.dataType = DataType.AREA.code
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter = MatchFilterAdapter(MatchFilterAdapter.TYPE_AREA, this)
        rvMatchItemFilter?.adapter = adapter
        adapter?.setAreaList(areaList)
    }

    fun setCountryData(countryList: MutableList<MatchList.Country>, areaId: Int, title: String?) {
        this.dataType = DataType.COUNTRY.code
        this.id = areaId
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter = MatchFilterAdapter(MatchFilterAdapter.TYPE_COUNTRY, this)
        rvMatchItemFilter?.adapter = adapter
        adapter?.setCountryList(countryList)
    }

    fun setLeagueData(leagueList: MutableList<MatchList.Leagues>, countryId: Int, title: String?) {
        this.dataType = DataType.LEAGUE.code
        this.id = countryId
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter = MatchFilterAdapter(MatchFilterAdapter.TYPE_LEAGUE, this)
        rvMatchItemFilter?.adapter = adapter
        adapter?.setLeagueList(leagueList)
    }

    fun setListener(mMatchFilterItemWidgetOnChangeListener: MatchFilterItemWidgetOnChangeListener) {
        listener = mMatchFilterItemWidgetOnChangeListener
    }

    fun allSelected() {
        adapter?.allSelected()
        when(dataType) {
            DataType.AREA.code -> {
               val areaList = adapter?.getAreaList()
                if(areaList != null) {
                    for(i in areaList.indices) {
                        changeStatus(areaList[i].id, areaList[i].name, true)
                    }
                }
            }
            DataType.COUNTRY.code -> {
                val countryList = adapter?.getCountryList()
                if(countryList != null) {
                    for(i in countryList.indices) {
                        changeStatus(countryList[i].id, countryList[i].name, true)
                    }
                }
            }
            DataType.LEAGUE.code -> {
                val leagueList = adapter?.getLeagueList()
                if(leagueList != null) {
                    for(i in leagueList.indices) {
                        changeStatus(leagueList[i].id, leagueList[i].name, true)
                    }
                }
            }
        }
    }

    fun allUnselected() {
        adapter?.allUnselected()
        when(dataType) {
            DataType.AREA.code -> {
                val areaList = adapter?.getAreaList()
                if(areaList != null) {
                    for(i in areaList.indices) {
                        changeStatus(areaList[i].id, areaList[i].name, false)
                    }
                }
            }
            DataType.COUNTRY.code -> {
                val countryList = adapter?.getCountryList()
                if(countryList != null) {
                    for(i in countryList.indices) {
                        changeStatus(countryList[i].id, countryList[i].name, false)
                    }
                }
            }
            DataType.LEAGUE.code -> {
                val leagueList = adapter?.getLeagueList()
                if(leagueList != null) {
                    for(i in leagueList.indices) {
                        changeStatus(leagueList[i].id, leagueList[i].name, false)
                    }
                }
            }
        }
    }

    override fun changeStatus(id: Int, name: String, isCheck: Boolean) {
        listener?.changeStatus(id, name, isCheck)
    }

}