package com.sirfootball.android.data.model

data class LoadTeamPennantsCompositeResponse (
    val info: GetTeamInfoResponse,
    val pp: LoadTeamPennantsResponse
)

