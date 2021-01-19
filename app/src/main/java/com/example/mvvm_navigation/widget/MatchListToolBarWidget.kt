package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.base.utils.DateUtils
import com.example.mvvm_navigation.R
import java.sql.Timestamp

class MatchListToolBarWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null
    private var tvWeek: TextView? = null
    private var tvDay: TextView? = null
    private var btnLastDay: ImageView? = null
    private var btnNextDay: ImageView? = null
    private var btnFilter: ImageView? = null
    private var changeDateListener: MatchListToolBarListener? = null
    private var nowTimestamp: Long = 0

    init {
        initView()
    }

    interface MatchListToolBarListener {
        fun changeDate(timestamp: Long)
        fun clickFilter()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_match_list_toolbar, this)
        tvWeek = view?.findViewById(R.id.tv_week)
        tvDay = view?.findViewById(R.id.tv_day)
        btnLastDay = view?.findViewById(R.id.btn_last_day)
        btnNextDay = view?.findViewById(R.id.btn_next_day)
        btnFilter = view?.findViewById(R.id.btn_filter)
        nowTimestamp = DateUtils.getTodayTimeStamp()
        changeDateText(DateUtils.getCalendarFromTimeStamp(nowTimestamp))

        btnLastDay?.setOnClickListener {
            nowTimestamp = DateUtils.getLastDayTimeStamp(nowTimestamp)
            changeDateText(DateUtils.getCalendarFromTimeStamp(nowTimestamp))
            changeDateListener?.changeDate(nowTimestamp)
        }

        btnNextDay?.setOnClickListener {
            nowTimestamp = DateUtils.getNextDayTimeStamp(nowTimestamp)
            changeDateText(DateUtils.getCalendarFromTimeStamp(nowTimestamp))
            changeDateListener?.changeDate(nowTimestamp)
        }

        btnFilter?.setOnClickListener {
            changeDateListener?.clickFilter()
        }
    }

    private fun changeDateText(date: DateUtils.DateObject) {
        tvWeek?.text = date.week
        tvDay?.text = date.day.toString()
    }

    fun setListener(listener: MatchListToolBarListener) {
        changeDateListener = listener
    }

}