package com.example.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

    const val YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss"
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val YYYYMMDD = "yyyyMMdd"
    const val MMDD_HHMM = "MM-dd HH:mm"
    const val MMDD = "MM-dd"
    const val HHMM = "HH:mm"
    const val EEEE = "EEEE"

    fun getTodayDatMillis(): Long {
        return System.currentTimeMillis()
    }

    fun convertTimestampToStringDate(timestamp: Int? = 0, format: String): String {
        return if (timestamp == null || timestamp == 0) {
            ""
        } else {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp.toLong() * 1000
            var date = formatDate(cal.time, format)
            date
        }
    }

    fun getCalendarFromTimeStamp(timestamp: Long): DateObject {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timestamp
        return DateObject(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH),
            getDayOfWeekString(cal.get(Calendar.DAY_OF_WEEK))
        )
    }

    private fun getDayOfWeekString(dayOfWeek: Int): String {
        var dayOfWeekString = ""
        when (dayOfWeek) {
            1 -> {
                dayOfWeekString = "SUN"
            }
            2 -> {
                dayOfWeekString = "MON"
            }
            3 -> {
                dayOfWeekString = "TUE"
            }
            4 -> {
                dayOfWeekString = "WED"
            }
            5 -> {
                dayOfWeekString = "THU"
            }
            6 -> {
                dayOfWeekString = "FRI"
            }
            7 -> {
                dayOfWeekString = "SAT"
            }
        }
        return dayOfWeekString
    }

    fun getLastDayTimeStamp(timestamp: Long): Long {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timestamp
        cal.add(Calendar.DATE, -1)
        return cal.timeInMillis
    }

    fun getNextDayTimeStamp(timestamp: Long): Long {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timestamp
        cal.add(Calendar.DATE, 1)
        return cal.timeInMillis
    }

    fun convertDateHasDash(date: String): String {
        return if (date.isEmpty()) {
            ""
        } else {
            val date = formatDate(date.toDate(YYYYMMDD)!!, YYYY_MM_DD)
            date
        }
    }

    fun convertDateNoDash(date: String): String {
        return if (date.isEmpty()) {
            ""
        } else {
            val date = formatDate(date.toDate(YYYY_MM_DD)!!, YYYYMMDD)
            date
        }
    }

    fun getRuntime(dateString: String): Int {

        val date = try {
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(dateString)
        } catch (e: Exception) {
            null
        }

        val toDay = Date()

        return ((toDay.time - date!!.time) / 1000 / 60).toInt()

    }

    /**
     * 計算該時間戳與現在時間的差距 返回到倒數文字
     * @param timeStampMills 時間戳(毫秒)
     * @return String
     */
    fun diffTimeString(timeStampMills: Long?): String {
        if (timeStampMills != null) {
            val timeDiff = diffTime(timeStampMills)
            if (timeDiff.hours > 0) {
                return String.format(
                    "倒數%s小時內",
                    timeDiff.hours
                )
            } else if (timeDiff.minutes > 0) {
                return String.format(
                    "倒數%s分鐘內",
                    timeDiff.minutes
                )
            }
        }
        return ""
    }

    /**
     * 計算該時間戳與現在時間的差距 返回TimeDiff物件
     * @param timeStampMills 時間戳(毫秒)
     * @return TimeDiff
     */
    fun diffTime(timeStampMills: Long): TimeDiff {
        val diffInMills = timeStampMills - System.currentTimeMillis()
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMills).toInt()
        val diffInMinute = TimeUnit.MILLISECONDS.toMinutes(diffInMills).toInt()
        return TimeDiff(diffInHours, diffInMinute)
    }

    private fun formatDate(date: Date, pattern: String? = "yyyy-MM-dd HH:mm:ss"): String =
        SimpleDateFormat(pattern, Locale.ENGLISH).format(date)

    /**
     * String轉日期
     */
    private fun String.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
        val sdFormat = try {
            SimpleDateFormat(pattern, Locale.ENGLISH)
        } catch (e: IllegalArgumentException) {
            null
        }
        return sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
    }

    data class DateObject(
        val year: Int = 0,
        val month: Int = 0,
        val day: Int = 0,
        val week: String = ""
    )

    data class TimeDiff(val hours: Int = 0, val minutes: Int = 0)

}