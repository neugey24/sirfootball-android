package com.sirfootball.android.data.model

data class LoadUserTeamsResponse (
    val result: String,
    val detail: String,
    val leaguesAvailable: Boolean,
    val userTeams: List<UserTeam>
)

data class UserTeam (
    val game_abbrev: String,
    val league_id: Int,
    val league_name: String,
    val draft_status: String,
    val draft_date: String,
    val competition_type: String,
    val scoring_type: String,
    val league_type: String,
    val team_id: Int,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val avatar_key: String,
    val team_num: Int
)