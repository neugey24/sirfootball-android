package com.sirfootball.android.data.model

data class LoadTeamTierRankingsCompositeResponse (
    val info: GetTeamInfoResponse,
    val tier: LoadTeamTierRankingsResponse,
    val roster: LoadTeamRosterResponse
)

