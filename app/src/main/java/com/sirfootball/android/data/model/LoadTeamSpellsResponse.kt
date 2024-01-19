package com.sirfootball.android.data.model

data class LoadTeamSpellsResponse (
    val result: String,
    val detail: String,

    val weekNum: Int,
    val spellChoiceData: Int,
    val spells: Map<String, SpellData>,

    val weekCommenced: Boolean
)

data class SpellData (
    val index: Int,
    val name: String,
    val image: String,
    val detail: String
)
