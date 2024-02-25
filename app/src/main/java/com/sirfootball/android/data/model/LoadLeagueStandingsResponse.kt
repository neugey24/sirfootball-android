package com.sirfootball.android.data.model

data class LoadLeagueStandingsResponse (
    val result: String,
    val detail: String,
    val standings: StandingsHolder
)

data class StandingsHolder (
    val league: LeagueInStandings,
    val divisions: List<DivisionInStandings>,
    val teams: List<String>
)

data class LeagueInStandings (
    val league_id: Int,
    val league_name: String,
    val league_abbrev: String,
    val game_abbrev: String,
    var divisions: Int,
    var division_names: String
)

data class DivisionInStandings (
    val name: String,
    val teams: List<TeamInStandings>
)

data class TeamInStandings (
    val team_type: String,
    val team_num: Int,
    val team_status: String,
    val team_id: Int,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val curr_wins: Int,
    val curr_losses: Int,
    val curr_pts_for: String,
    val curr_pts_against: String,
    val eliminated: Int,
    val avatar_key: String,
    val pt_diff: String
)