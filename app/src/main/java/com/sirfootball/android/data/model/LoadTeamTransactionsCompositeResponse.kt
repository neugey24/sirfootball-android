package com.sirfootball.android.data.model

data class LoadTeamTransactionsCompositeResponse (
    val info: GetTeamInfoResponse,
    val tran: LoadTeamTransactionsResponse
)

