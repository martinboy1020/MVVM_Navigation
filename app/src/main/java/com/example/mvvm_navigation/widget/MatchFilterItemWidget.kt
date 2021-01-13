package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.filter.MatchFilterAdapter

class MatchFilterItemWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var attrs: AttributeSet? = null
    private var view: View? = null
    private var rvMatchItemFilter: BuildRecyclerView? = null
    private var adapter: MatchFilterAdapter? = null
    private var tvMatchFilterItemTitle: TextView? = null
    var dataType: Int? = null
    var id: Int? = null

    enum class DataType(val code: Int) {
        AREA(0), COUNTRY(1), LEAGUE(2)
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
        adapter = MatchFilterAdapter(MatchFilterAdapter.TYPE_AREA)
        rvMatchItemFilter?.adapter = adapter
        adapter?.setAreaList(areaList)
    }

    fun setCountryData(countryList: MutableList<MatchList.Country>, areaId: Int, title: String?) {
        this.dataType = DataType.COUNTRY.code
        this.id = areaId
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter = MatchFilterAdapter(MatchFilterAdapter.TYPE_COUNTRY)
        rvMatchItemFilter?.adapter = adapter
        adapter?.setCountryList(countryList)
    }

    fun setLeagueData(leagueList: MutableList<MatchList.Leagues>, countryId: Int, title: String?) {
        this.dataType = DataType.LEAGUE.code
        this.id = countryId
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter = MatchFilterAdapter(MatchFilterAdapter.TYPE_LEAGUE)
        rvMatchItemFilter?.adapter = adapter
        adapter?.setLeagueList(leagueList)
    }

}