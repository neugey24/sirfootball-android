package com.sirfootball.android.data.model

data class LoadLeagueTransactionsResponse (
    val result: String,
    val detail: String,
    val transactions: List<LeagueTransactionInfo>
)

data class LeagueTransactionInfo (
    val tran_id: Int,
    val tran_type: String,
    val tran_status: String,
    val player_id: Int,
    val created_date: String,
    val fantasy_position: String,
    val full_name: String,
    val team_abbrev: String?,
    val fantasy_team_abbrev: String,
)
