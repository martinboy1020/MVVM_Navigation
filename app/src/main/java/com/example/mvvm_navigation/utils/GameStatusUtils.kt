package com.example.mvvm_navigation.utils

/**
 * 判斷賽事狀態工具類
 */
object GameStatusUtils {

    class MatchStatus {

        /**
         * 比賽狀態碼
         */
        companion object {
            // 比賽異常
            const val ABNORMAL = 0

            // 尚未開賽
            const val UNOPENED = 1

            // 上半場
            const val FIRST_HALF = 2

            // 中場
            const val HALF_TIME = 3

            // 下半場
            const val SECOND_HALF = 4

            // 加時賽
            const val OVERTIME = 5

            // 加時賽
            const val OVERTIME_2 = 6

            // 點球決戰
            const val PK = 7

            // 完賽
            const val ENDING = 8

            // 推遲
            const val POSTPONE = 9

            // 中斷
            const val SUSPEND = 10

            // 腰斬
            const val WAIST = 11

            // 取消
            const val CANCEL = 12

            // 待定
            const val AWAITING = 13

            // 回傳賽事狀態文字
            fun getGameStatusString(status: Int): String {
                var gameStatus = ""
                when (status) {
//                ABNORMAL -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.abnormal_game)
//                UNOPENED -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.game_coming_soon)
//                FIRST_HALF -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.first_half)
//                HALF_TIME -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.midfield)
//                SECOND_HALF -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.second_half)
//                OVERTIME, OVERTIME_2 -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.game_overtime)
//                PK -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.game_penalty_shootout)
//                ENDING -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.ending)
//                POSTPONE -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.put_off)
//                SUSPEND -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.interrupt)
//                WAIST -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.waist)
//                CANCEL -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.common_cancel)
//                AWAITING -> gameStatus = MainApplication.getCurrentActivity().getString(R.string.to_be_determined)
                }
                return gameStatus
            }

            // 檢查賽事狀態是否已開賽
            fun checkGameMatchIsStarted(status: Int): Boolean {
                return status == FIRST_HALF ||
                        status == HALF_TIME ||
                        status == SECOND_HALF ||
                        status == OVERTIME ||
                        status == OVERTIME_2 ||
                        status == PK
            }

            // 大家都在關注賽事進行中時間
            fun getGameRunTimeStringForHomePopular(runtime: Int, status: Int): String {

                var gameRunTime = ""

                when (status) {
                    FIRST_HALF -> {
//                    gameRunTime = if (runtime > 45) {
//                        "45+"
//                    } else {
//                        runtime.toString() + MainApplication.getCurrentActivity().getString(R.string.first_half_2)
//                    }
                    }
                    SECOND_HALF -> {
//                    gameRunTime = if (runtime > 90) {
//                        "90+"
//                    } else {
//                        runtime.toString() + MainApplication.getCurrentActivity().getString(R.string.second_half_2)
//                    }
                    }
                    OVERTIME, OVERTIME_2, PK -> {
                        gameRunTime = "90+"
                    }
                }

                return gameRunTime

            }

            // 檢查賽事狀態是否為異動狀態
            fun checkMatchTransaction(status: Int): Boolean {
                return status == ABNORMAL || status == POSTPONE || status == SUSPEND || status == WAIST || status == CANCEL || status == AWAITING
            }

            // 檢查賽事狀態是否為進行中
            fun checkMatchIsIng(status: Int): Boolean {
                return status == FIRST_HALF || status == SECOND_HALF || status == HALF_TIME || status == OVERTIME || status == OVERTIME_2 || status == PK
            }

        }

    }

    /**
     * 統計比分狀態碼
     */
    class StatisticsStatus {

        companion object {
            const val NOTHING = 0
            const val GOAL = 1
            const val CORNER = 2
            const val YELLOW_CARD = 3
            const val RED_CARD = 4
            const val OUT_BOUND_BALL = 5
            const val FREE_KICK = 6
            const val GOAL_KICK = 7
            const val PENALTY_KICK = 8
            const val SUBSTITUTE = 9
            const val GAME_START = 10
            const val HALF_TIME = 11
            const val ENDING = 12
            const val HALF_SCORE = 13
            const val TWO_YELLOW_TO_RED = 15
            const val PENALTY_KICK_NOT_GOAL = 16
            const val OWN_GOAL = 17
            const val INJURY_TIME = 19
            const val SHOOT_THE_GOAL = 21
            const val MISS_THE_GOAL = 22
            const val OFFENSIVE = 23
            const val DANGEROUS_OFFENSIVE = 24
            const val POSSESSION_RATE = 25
            const val OVER_TIME_ENDING = 26
            const val PENALTY_KICK_ENDING = 27
            const val VAR = 28
        }

    }

}