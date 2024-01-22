package com.sirfootball.android.data.model

data class LoadTeamTransactionsResponse (
    val result: String,
    val detail: String,
    val transactions: List<TransactionInfo>
)

data class TransactionInfo (
    val tran_id: Int,
    val tran_type: String,
    val tran_status: String,
    val player_id: Int,
    val created_date: String,
    val fantasy_position: String,
    val full_name: String,
    val team_abbrev: String?
)
