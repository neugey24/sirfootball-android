package com.sirfootball.android.data.model

data class LoadTeamPicksCompositeResponse (
    val info: GetTeamInfoResponse,
    val picks: LoadTeamPicksResponse
)

