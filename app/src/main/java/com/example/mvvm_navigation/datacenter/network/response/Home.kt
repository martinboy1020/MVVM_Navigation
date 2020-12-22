package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class Home {
    data class WebHomeInfo(
        @SerializedName(ApiDataKey.BANNERS) val banners: MutableList<Banners> = mutableListOf(),
        @SerializedName(ApiDataKey.NEWS) val news: MutableList<News> = mutableListOf()
    )

    data class Banners(
        @SerializedName(ApiDataKey.TITLES) val titles: String = "",
        @SerializedName(ApiDataKey.TYPE) val type: Int = 0,
        @SerializedName(ApiDataKey.IMG_URL) val imgUrl: String = "",
        @SerializedName(ApiDataKey.LINK_URL) val linkUrl: String = ""
    )

    data class News(
        @SerializedName(ApiDataKey.ID) val titles: Int = 0,
        @SerializedName(ApiDataKey.TYPE) val type: Int,
        @SerializedName(ApiDataKey.IMG_SRC) val imgSrc: String = "",
        @SerializedName(ApiDataKey.TIME) val time: String = ""
    )

    data class WebHomeMatchesStatistics(@SerializedName(ApiDataKey.STATISTIC_DATA) val statisticsData: StatisticsData)
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
        @SerializedName(ApiDataKey.CONTINUED_COUNT) val continuedCount: Int = 0,
        @SerializedName(ApiDataKey.CONTINUED_SEASONS) val continuedSeasons: Int = 0,
        @SerializedName(ApiDataKey.NO_CONTINUED_COUNT) val notContinuedCount: Int = 0,
        @SerializedName(ApiDataKey.NO_CONTINUED_SEASONS) val notContinuedSeasons: Int = 0,
        @SerializedName(ApiDataKey.CONTINUED_RATE) val continuedRate: String = "",
        @SerializedName(ApiDataKey.WIN_RATE) val winRate: Int = 0
    )

    data class WebHomeMatchesBetInfo(
        @SerializedName(ApiDataKey.PAYLOAD) val payload: BetInfo
    )

    data class BetInfo(
        @SerializedName("0-0") val score00: BetInfoDetail,
        @SerializedName("0-1") val score01: BetInfoDetail,
        @SerializedName("0-2") val score02: BetInfoDetail,
        @SerializedName("0-3") val score03: BetInfoDetail,
        @SerializedName("1-0") val score10: BetInfoDetail,
        @SerializedName("1-1") val score11: BetInfoDetail,
        @SerializedName("1-2") val score12: BetInfoDetail,
        @SerializedName("1-3") val score13: BetInfoDetail,
        @SerializedName("2-0") val score20: BetInfoDetail,
        @SerializedName("2-1") val score21: BetInfoDetail,
        @SerializedName("2-2") val score22: BetInfoDetail,
        @SerializedName("2-3") val score23: BetInfoDetail,
        @SerializedName("3-0") val score30: BetInfoDetail,
        @SerializedName("3-1") val score31: BetInfoDetail,
        @SerializedName("3-2") val score32: BetInfoDetail,
        @SerializedName("3-3") val score33: BetInfoDetail,
        @SerializedName("homeFourRoMore") val homeFourRoMore: BetInfoDetail,
        @SerializedName("awayFourRoMore") val awayFourRoMore: BetInfoDetail
    )

    data class BetInfoDetail(
        @SerializedName(ApiDataKey.TRANSACTION) val transaction: String = "",
        @SerializedName(ApiDataKey.PROFIT) val profit: String = "",
        @SerializedName(ApiDataKey.BET_MONEY) val betMoney: String = "",
        @SerializedName(ApiDataKey.IS_POPULAR) val isPopular: Int = 0,
        @SerializedName(ApiDataKey.IS_SOLD_OUT) val isSoldOut: Int = 0
    )
}