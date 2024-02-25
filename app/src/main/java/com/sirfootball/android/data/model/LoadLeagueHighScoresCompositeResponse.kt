package com.sirfootball.android.data.model

data class LoadLeagueHighScoresCompositeResponse (
    val info: GetLeagueInfoResponse,
    val high: LoadLeagueHighScoresResponse
)

