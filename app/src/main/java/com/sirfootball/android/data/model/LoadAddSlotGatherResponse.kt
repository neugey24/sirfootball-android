package com.sirfootball.android.data.model

data class LoadAddSlotGatherResponse (
    val result: String,
    val detail: String,
    val data: AddSlotGatherData
)

data class AddSlotGatherData (
    val weekNum: Int,
    val gamesByNflTeam: Map<String, ScheduledGameData>,
    val playerRecord: AddSlotPlayerRecord,
    val slots: Map<String, SlotForAdd>
)

data class AddSlotPlayerRecord (
    val nfl_player_id: Int,
    val full_name: String,
    val fantasy_position: String,
    val injury_status: String,
    val team_abbreviation: String?,
    val bye_week: Int
)

data class SlotForAdd (
    val slot_id: Int,
    val team_id: Int,
    val slot_name: String,
    val slot_pos: String?,
    val player_id: Int?,
    val slot_type: String,
    val dr_player_id: Int?,
    val is_dr: Int?,
    val priority_num: Int?,
    val proc_day: String?,
    val claim_item_id: Int?,
    val claim_player_id: Int?,
    val association_lock: Int?,

    val player: PlayerInfo?,
    val locked: Boolean,
    var eligible: Boolean
)

