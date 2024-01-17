package com.sirfootball.android.data.model

data class LoadTeamAddPlayerResponse (
    val result: String,
    val detail: String,
    val data: AddPlayerResponseDetails
)

data class AddPlayerResponseDetails (
    val posAvailable: List<String>,
    val gamesByNflTeam: Map<String, ScheduledGameData>,
    val players: List<PlayerForAddData>,
    val replacingPlayer: PlayerInfo?,

    val slotName: String,
    val isDr: String,
    val posFromReq: String,
    val posView: String
)

data class PlayerForAddData (
    val fantasy_position: String = "",
    val nfl_team_name: String = "",
    val full_name: String = "",
    val injury_status: String = "",
    val team_abbreviation: String? = "",
    val bye_week: Int = -1,
    val clear_day: String? = null,
    val clear_item_id: Int? = null,
    val association_lock: Int = -1,
    val nfl_player_id: Int = -1,
    val is_free_agent: String = "N",
    val fpoints: String? = "",
    val fppg: String? = "",
    val caliber: Int = -1,
    val stat_brief: String = ""  
)

