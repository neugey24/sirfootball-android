package com.sirfootball.android.data.model

data class LoadTeamSpellsCompositeResponse (
    val info: GetTeamInfoResponse,
    val spells: LoadTeamSpellsResponse
)

