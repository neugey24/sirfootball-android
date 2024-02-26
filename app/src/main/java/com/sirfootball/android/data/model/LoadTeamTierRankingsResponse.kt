package com.sirfootball.android.data.model

data class LoadTeamTierRankingsResponse (
    val result: String,
    val detail: String,

    val weekNum: Int,
    val tierData: Map<String, String>,

    val weekCommenced: Boolean
)