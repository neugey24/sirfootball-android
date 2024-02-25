package com.sirfootball.android.data.model

data class LoadLeagueTransactionsCompositeResponse (
    val info: GetLeagueInfoResponse,
    val trans: LoadLeagueTransactionsResponse
)

