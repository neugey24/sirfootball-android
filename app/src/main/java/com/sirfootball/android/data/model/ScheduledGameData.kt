package com.sirfootball.android.data.model

data class ScheduledGameData (
    val home_team_abbrev: String,
    val away_team_abbrev: String,
    val home_team_city: String,
    val home_team_name: String,
    val away_team_city: String,
    val away_team_name: String,
    val game_start_date: String,
    val week_number: Int,
    val game_status: String,
    val happyTime: String
)