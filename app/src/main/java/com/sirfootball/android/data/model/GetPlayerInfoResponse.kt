package com.sirfootball.android.data.model

data class GetPlayerInfoResponse (
    val result: String,
    val detail: String,
    val info: PlayerInfoHolder
)

data class PlayerInfoHolder (
    val playerInfo: PlayerInfo,
    val weekNumber: Int,
    val gamesByWeek: Map<String, ProWeeklyMatchup>
)

data class PlayerInfo (
    val id: Int,
    val pos: String,
    val inj: InjuryInfo?,
    val bye: Int,
    val team: String?,
    val teamCity: String?,
    val teamName: String?,
    val fullName: String,
    val shortName: String,
    val jerseyNum: Int,
    val initialRank: Int,
    val fpoints: String?,
    val fppgRank: Int?,
    val fppg: String?,
    val caliber: Int,

    val lss: PlayerStatSummary?,
    val tss: PlayerStatSummary?,
    val wbws: Map<String, WeekByWeekStats>?
)

data class WeekByWeekStats (
    val games: Int,
    val sacks: Int = -1,
    val td: Int = -1,
    val fumRecov: Int = -1,
    val int: Int = -1,
    val safeties: Int = -1,
    val pa: Int = -1,
    val brief: String?,

    val pass: PassStatSummary?,
    val rush: RushStatSummary?,
    val rec: RecStatSummary?,
    val fg: FGStatSummary?,
    val xp: XPStatSummary?
)

data class ProWeeklyMatchup (
    val vendorId: String,
    val status: String,
    val week: Int,
    val startDate: String,
    val happyTime: String,
    val homeGame: Boolean,
    val opponent: MatchupOpponentInfo?
)

data class MatchupOpponentInfo (
    val city: String,
    val name: String,
    val abbrev: String
)

data class InjuryInfo (
    val status: String,
    val date: String,
    val notes: String
)

data class PlayerStatSummary (
    val games: Int?,
    val pass: PassStatSummary?,
    val rush: RushStatSummary?,
    val rec: RecStatSummary?,
    val fg: FGStatSummary?,
    val xp: XPStatSummary?,
    val brief: String?,

    val sacks: Int?,
    val td: Int?,
    val fumRecov: Int?,
    val int: Int?,
    val safeties: Int?,
    val pa: Int?
)

data class PassStatSummary (
    val yards: Int,
    val td: Int,
    val int: Int,
    val ypg: Double
)

data class RushStatSummary (
    val yards: Int,
    val td: Int,
    val ypc: Double
)

data class RecStatSummary (
    val rec: Int,
    val yards: Int,
    val td: Int,
    val ypr: Double
)

data class FGStatSummary (
    val att: Int,
    val made_under50: Int,
    val made_50plus: Int,
    val pct: Double
)

data class XPStatSummary (
    val att: Int,
    val made: Int,
    val pct: Double
)

