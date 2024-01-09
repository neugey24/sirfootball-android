package com.sirfootball.android.data.model

data class GetLeagueInfoResponse (
    val result: String,
    val detail: String,
    val leagueInfo: LeagueInfo
)

data class LeagueInfo (
    val league_id: Int,
    val league_name: String,
    val competition_type: String,
    val game_abbrev: String,
    val scoring_type: String,
    val league_status: String,
    val draft_status: String,
    val draft_date: String
)