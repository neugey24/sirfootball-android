package com.sirfootball.android.data.model

data class LoadJoinLeagueListResponse (
    val result: String,
    val detail: String,
    val leaguesAvailable: Boolean,
    val openLeagues: Map<String, List<AvailableLeague>>
)

data class AvailableLeague (
    val league_id: Int = -1,
    val league_name: String = "",
    val competition_type: String = "",
    val game_abbrev: String = "",
    val league_type: String = "",
    val scoring_type: String = "",
    val draft_status: String = "",
    val draft_date: String = "",
    val robo_team_count: Int = -1,
    val league_size: Int = -1,
    val teamCount: Int = -1,
    val joinType: String = ""
)