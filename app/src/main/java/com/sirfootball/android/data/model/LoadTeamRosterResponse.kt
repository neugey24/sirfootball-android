package com.sirfootball.android.data.model

data class LoadTeamRosterResponse (
    val result: String,
    val detail: String,
    val info: TeamRosterInfo
)

data class TeamRosterInfo (
    val slots: Map<String, RosterSlotInfo>,
    val weekNumber: Int,
    val fantasyBye: Boolean,
    val weeklySchedule: Map<String, ScheduledGameData>,
    val waiverTimeframe: String,
    val weekCommenced: Boolean
)

data class RosterSlotInfo (
    val slotId: Int,
    val slotName: String,
    val slotPos: String,
    val slotType: String,

    val isEmpty: Boolean,
    val isLocked: Boolean,
    val lockReason: String,
    val lockPlayerInfo: LockPlayerInfo?,
    val slotPlayer: SlotPlayer?,
    val availableActions: List<RosterAction>,

    val claimInfo: WaiverClaimInfo?
)

data class WaiverClaimInfo (
    val claimItemId: Int,
    val procDay: String,
    val prioNum: Int
)

data class SlotPlayer (
    val playerId: Int,
    val position: String,
    val shortName: String,
    val fullName: String,
    val teamAbbrev: String?,
    val byeWeek: Int,
    val caliber: Int,
    val jerseyNum: Int?,
    val fantasyPoints: String?,
    val hasInjury: Boolean,
    val gameInfo: SlotPlayerGameInfo?
)

data class SlotPlayerGameInfo (
    val statBrief: String,
    val gameStatus: String
)

data class RosterAction (
    val type: String,
    val sourceSlot: String?,
    val destSlot: String?,
    val description: String
)

data class LockPlayerInfo (
    val playerId: Int,
    val position: String,
    val shortName: String,
    val longName: String,
    val teamAbbrev: String
)
