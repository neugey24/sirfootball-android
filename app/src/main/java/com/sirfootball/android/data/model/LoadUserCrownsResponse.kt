package com.sirfootball.android.data.model

data class LoadUserCrownsResponse (
    val result: String,
    val detail: String,
    val crowns: List<Crown>
)

data class Crown (
    val name: String,
    val image: String,
    val type: String,
    val detail: String,
    val rewardDate: String,
    val awardNotes: String
)