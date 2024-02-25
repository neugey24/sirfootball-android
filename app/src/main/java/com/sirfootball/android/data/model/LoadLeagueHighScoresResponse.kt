package com.sirfootball.android.data.model

data class LoadLeagueHighScoresResponse (
    val result: String,
    val detail: String,
    val highScores: List<HighScoreInfo>
)

data class HighScoreInfo (
    val high_score: String,
    val team_id: Int,
    val link: String,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val week_num: String,
    val opp_abbrev: String,
    val user_id: Int?

)
