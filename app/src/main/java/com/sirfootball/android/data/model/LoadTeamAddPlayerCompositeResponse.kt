package com.sirfootball.android.data.model

data class LoadTeamAddPlayerCompositeResponse (
    val info: GetTeamInfoResponse,
    val add: LoadTeamAddPlayerResponse
)

