package com.sirfootball.android.data.model

data class LoadPreviewResponse (
    val result: String,
    val detail: String,

    val preview: PreviewData,
    val gamesByNflTeam: Map<String, ScheduledGameData>,
    val weekNum: Int,
    val matchupTitle: String
)

data class PreviewData (
    val teamInfo: PreviewTeamInfo,
    val oppInfo: PreviewTeamInfo,
    val slots: Map<String, PreviewTeamSlot>,
    val oppSlots: Map<String, PreviewTeamSlot>
)

data class PreviewTeamSlot (
    val slot_name: String,
    val slot_pos: String,
    val player_id: Int,
    val player: PreviewTeamPlayer?,
)

data class PreviewTeamPlayer (
    val id: Int,
    val pos: String,
    val team: String?,
    val jerseyNum: Int?,
    val shortName: String,
    val fullName: String,
    val fpoints: String?,
    val caliber: Int?,
    val tss_brief: String
)

data class PreviewTeamInfo (
    val competition_type: String?,
    val game_abbrev: String?,
    val team_id: Int,
    val league_id: Int,
    val team_num: Int,
    val team_city: String,
    val team_name: String,
    val team_abbrev: String,
    val curr_wins: Int,
    val curr_losses: Int,
    val avatar_key: String,
    val league_name: String?
)

