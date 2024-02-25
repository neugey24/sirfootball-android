package com.sirfootball.android.data.model

data class LoadTeamPicksResponse (
    val result: String,
    val detail: String,

    val weekNum: Int,
    val games: Map<String, ScheduledGameData>,
    val picks: Map<String, GamePickData>,

    val weekCommenced: Boolean
)

data class GamePickData (
    val home: String,
    val away: String,
    val selection: String,
    val correct: Boolean,
    val final: Boolean
)
