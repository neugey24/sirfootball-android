package com.sirfootball.android.data.model

data class LoadTeamDoubleDownCompositeResponse (
    val info: GetTeamInfoResponse,
    val roster: LoadTeamRosterResponse,
    val dd: LoadTeamDoubleDownResponse
)

