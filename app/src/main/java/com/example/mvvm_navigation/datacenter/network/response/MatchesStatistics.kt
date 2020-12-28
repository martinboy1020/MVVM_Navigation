package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

class MatchesStatistics {

    data class Data(
        @SerializedName(ApiDataKey.STATISTIC_DATA) val statisticsData: StatisticsData,
        @SerializedName(ApiDataKey.GOAL_DATA) val goalData: GoalAndLostData,
        @SerializedName(ApiDataKey.LOST_DATA) val lostData: GoalAndLostData,
        @SerializedName(ApiDataKey.TOTAL_COUNT) val totalCount: Int = 0,
        @SerializedName(ApiDataKey.SEASONS) var seasons: MutableList<Season>
    )

    data class StatisticsData(
        @SerializedName("0-0") val score00: StaticDetailData,
        @SerializedName("0-1") val score01: StaticDetailData,
        @SerializedName("0-2") val score02: StaticDetailData,
        @SerializedName("0-3") val score03: StaticDetailData,
        @SerializedName("1-0") val score10: StaticDetailData,
        @SerializedName("1-1") val score11: StaticDetailData,
        @SerializedName("1-2") val score12: StaticDetailData,
        @SerializedName("1-3") val score13: StaticDetailData,
        @SerializedName("2-0") val score20: StaticDetailData,
        @SerializedName("2-1") val score21: StaticDetailData,
        @SerializedName("2-2") val score22: StaticDetailData,
        @SerializedName("2-3") val score23: StaticDetailData,
        @SerializedName("3-0") val score30: StaticDetailData,
        @SerializedName("3-1") val score31: StaticDetailData,
        @SerializedName("3-2") val score32: StaticDetailData,
        @SerializedName("3-3") val score33: StaticDetailData,
        @SerializedName("homeFourRoMore") val homeFourRoMore: StaticDetailData,
        @SerializedName("awayFourRoMore") val awayFourRoMore: StaticDetailData,
        @SerializedName(ApiDataKey.MAX_SCORE) val maxScore: String = "",
        @SerializedName(ApiDataKey.MIN_SCORE) val minScore: String = "",
        @SerializedName(ApiDataKey.MAX_VALUE) val maxValue: String = "",
        @SerializedName(ApiDataKey.MIN_VALUE) val minValue: String = ""
    )

    data class StaticDetailData(
        @SerializedName(ApiDataKey.APPEAR_CNT) val appearCnt: Int = 0,
        @SerializedName(ApiDataKey.NO_APPEAR_CNT) val notAppearCnt: Int = 0,
        @SerializedName(ApiDataKey.APPEAR_RATE) val appearRate: String = "",
        @SerializedName(ApiDataKey.NOT_APPEAR_RATE) val notAppearRate: String = "",
        @SerializedName(ApiDataKey.CONTINUED_COUNT) val continuedCount: Int = 0,
        @SerializedName(ApiDataKey.CONTINUED_SEASONS) val continuedSeasons: Int = 0,
        @SerializedName(ApiDataKey.NO_CONTINUED_COUNT) val notContinuedCount: Int = 0,
        @SerializedName(ApiDataKey.NO_CONTINUED_SEASONS) val notContinuedSeasons: Int = 0,
        @SerializedName(ApiDataKey.CONTINUED_RATE) val continuedRate: String = "",
        @SerializedName(ApiDataKey.WIN_RATE) val winRate: String = ""
    )

    data class GoalAndLostData(
        @SerializedName(ApiDataKey.ONE) val one: StaticDetailData,
        @SerializedName(ApiDataKey.TWO) val two: StaticDetailData,
        @SerializedName(ApiDataKey.THREE) val three: StaticDetailData,
        @SerializedName(ApiDataKey.FOUR_OR_MORE) val fourOrMore: StaticDetailData,
        @SerializedName(ApiDataKey.AVERAGE_GOAL) val avgGoal: String = "0",
        @SerializedName(ApiDataKey.AVERAGE_LOST) val avgLost: String = "0",
        var type: GoalOrLostType = GoalOrLostType.GOAL,
        var isDisappearStatus: Boolean = false,
        var totalCount: Int = 0
    )

    data class Season(
        @SerializedName(ApiDataKey.ID) val id: String = "",
        @SerializedName(ApiDataKey.NAME) val name: String = ""
    )

}

enum class GoalOrLostType {
    GOAL, LOST
}