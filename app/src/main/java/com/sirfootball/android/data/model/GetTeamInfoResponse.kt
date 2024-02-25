package com.sirfootball.android.data.model

data class GetTeamInfoResponse (
    val result: String,
    val detail: String,
    val matchupSummary: String,
    val weekNum: Int,
    val matchupNum: Int,
    val gameLive: Boolean,
    val teamInfo: TeamInfo
)

data class TeamInfo (
    val team_id: Int,
    val team_num: Int,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val curr_wins: Int,
    val curr_losses: Int,
    val eliminated: Int,
    val avatar_key: String,
    val game_abbrev: String,
    val league_id: Int,
    val postseason_seed: Int,
    val competition_type: String,
    val league_type: String,
    val league_name: String,
    val league_status: String,
    val draft_status: String,
    val draft_date: String
)