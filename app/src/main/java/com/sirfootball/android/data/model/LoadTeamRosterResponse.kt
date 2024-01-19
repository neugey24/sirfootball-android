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
    val slotId: Int = -1,
    val slotName: String = "",
    val slotPos: String = "",
    val slotType: String = "",

    val isEmpty: Boolean = false,
    val isLocked: Boolean = false,
    val lockReason: String = "",
    val lockPlayerInfo: LockPlayerInfo? = null,
    val slotPlayer: SlotPlayer? = null,
    val availableActions: List<RosterAction> = emptyList(),

    val claimInfo: WaiverClaimInfo? = null
)

data class WaiverClaimInfo (
    val claimItemId: Int,
    val procDay: String,
    val prioNum: Int
)

data class SlotPlayer (
    val playerId: Int = -1,
    val position: String = "",
    val shortName: String = "",
    val fullName: String = "",
    val teamAbbrev: String? = null,
    val byeWeek: Int = -1,
    val caliber: Int = -1,
    val jerseyNum: Int? = -1,
    val fantasyPoints: String? = null,
    val hasInjury: Boolean = false,
    val gameInfo: SlotPlayerGameInfo? = null
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
