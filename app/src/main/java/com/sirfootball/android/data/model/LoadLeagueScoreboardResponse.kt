package com.sirfootball.android.data.model

data class LoadLeagueScoreboardResponse (
    val result: String,
    val detail: String,
    val matchUps: List<LeagueMatchUpData>,
    val scorecards: Map<String, Double>,
    val teams: Map<String, LeagueScoreboardTeam>,
    val weekNum: Int
)

data class LeagueMatchUpData (
    val name: String,
    val abbrev: String,
    val team: Int,
    val opp: Int,
    val type: String,
    val subtype: String?,
    val linkNum: Int
)

data class LeagueScoreboardTeam (
    val team_id: Int,
    val team_num: Int,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val avatar_key: String,
    val postseason_seed: Int,
    val po_seed_1: Int,
    val po_seed_2: Int
)
