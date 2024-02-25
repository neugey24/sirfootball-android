package com.sirfootball.android.data.model

data class LoadTeamDoubleDownResponse (
    val result: String,
    val detail: String,

    val weekNum: Int,
    val dd: Map<String, Int>,

    val weekCommenced: Boolean
)
