package com.sirfootball.android.data.model

data class LoadTeamScheduleResponse (
    val result: String,
    val detail: String,
    val schedule: List<ScheduleInfo>
)

data class ScheduleInfo (
    val gameNum: Int,
    val played: Boolean,
    val week: Int,
    val gameType: String,
    val subType: String,
    val scorecardTeamNum: Int,
    val opponent: ScheduleOpponentInfo?,
    val result: ScheduleResult?,
    val bye: String
)

data class ScheduleOpponentInfo (
    val team_id: Int,
    val team_num: Int,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val postseason_seed: Int,
    val po_seed_1: Int,
    val po_seed_2: Int
)

data class ScheduleResult (
    val score: String,
    val opp_score: String,
    val win: Int
)
