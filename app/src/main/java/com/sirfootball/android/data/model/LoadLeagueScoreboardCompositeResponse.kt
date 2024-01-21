package com.sirfootball.android.data.model

data class LoadLeagueScoreboardCompositeResponse (
    val info: GetLeagueInfoResponse,
    val scoreboard: LoadLeagueScoreboardResponse
)

