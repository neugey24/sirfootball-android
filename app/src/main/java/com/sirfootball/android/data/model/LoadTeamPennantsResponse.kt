package com.sirfootball.android.data.model

data class LoadTeamPennantsResponse (
    val result: String,
    val detail: String,

    val weekNum: Int,
    val pennantData: Map<String, String>,
    val pennants: Map<String, PennantInfo>,

    val weekCommenced: Boolean
)

data class PennantInfo (
    val color: String,
    val name: String,
    val type: String,
    val image: String,
    val detail: String
)
