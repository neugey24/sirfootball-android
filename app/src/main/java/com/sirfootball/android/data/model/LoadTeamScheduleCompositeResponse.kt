package com.sirfootball.android.data.model

data class LoadTeamScheduleCompositeResponse (
    val info: GetTeamInfoResponse,
    val schedule: LoadTeamScheduleResponse
)

