package com.sirfootball.android.data.model

data class LoadTeamWeeklySpecialResponse (
    val result: String,
    val detail: String,

    val weekNum: Int,
    val special: SpecialData,

    val specialKey: String
)

data class SpecialData (
    val name: String,
    val image: String,
    val detail: String
)
