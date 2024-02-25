package com.sirfootball.android.data.model

data class LoadTeamWeeklySpecialCompositeResponse (
    val info: GetTeamInfoResponse,
    val special: LoadTeamWeeklySpecialResponse
)

