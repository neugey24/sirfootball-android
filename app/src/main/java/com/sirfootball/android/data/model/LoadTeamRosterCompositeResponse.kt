package com.sirfootball.android.data.model

data class LoadTeamRosterCompositeResponse (
    val info: GetTeamInfoResponse,
    val roster: LoadTeamRosterResponse
)

