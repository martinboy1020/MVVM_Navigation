package com.example.mvvm_navigation.widget.match_filter_widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.widget.BuildRecyclerView

// 賽事篩選Row(各個洲別, 國家, 賽事)
class MatchFilterRowWidget @JvmOverloads constructor(
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
        fun changeStatusFromRowWidget(id: Int, name: String, isCheck: Boolean)
    }

    init {
        this.attrs = attrs
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_match_filter_row_widget, this)
        rvMatchItemFilter = view?.findViewById(R.id.rv_match_item_filter)
        tvMatchFilterItemTitle = view?.findViewById(R.id.tv_match_filter_item_title)
    }

    fun setAreaData(areaList: MutableList<MatchList.Area>, title: String?) {
        this.dataType = DataType.AREA.code
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter =
            MatchFilterAdapter(
                MatchFilterAdapter.TYPE_AREA,
                this
            )
        rvMatchItemFilter?.adapter = adapter
        adapter?.setAreaList(areaList)
    }

    fun setCountryData(countryList: MutableList<MatchList.Country>, areaId: Int, title: String?) {
        this.dataType = DataType.COUNTRY.code
        this.id = areaId
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter =
            MatchFilterAdapter(
                MatchFilterAdapter.TYPE_COUNTRY,
                this
            )
        rvMatchItemFilter?.adapter = adapter
        adapter?.setCountryList(countryList)
    }

    fun setLeagueData(leagueList: MutableList<MatchList.Leagues>, countryId: Int, title: String?) {
        this.dataType = DataType.LEAGUE.code
        this.id = countryId
        if(title.isNullOrEmpty()) tvMatchFilterItemTitle?.visibility = View.GONE else View.VISIBLE
        tvMatchFilterItemTitle?.text = title
        adapter =
            MatchFilterAdapter(
                MatchFilterAdapter.TYPE_LEAGUE,
                this
            )
        rvMatchItemFilter?.adapter = adapter
        adapter?.setLeagueList(leagueList)
    }

    fun setListener(mMatchFilterItemWidgetOnChangeListener: MatchFilterItemWidgetOnChangeListener) {
        listener = mMatchFilterItemWidgetOnChangeListener
    }

    fun allSelected() {
        adapter?.allSelected()
    }

    fun allUnselected() {
        adapter?.allUnselected()
    }

    override fun changeStatus(id: Int, name: String, isCheck: Boolean) {
        listener?.changeStatusFromRowWidget(id, name, isCheck)
    }

}