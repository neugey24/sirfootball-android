package com.sirfootball.android.data.model

data class LoadScorecardResponse (
    val result: String,
    val detail: String,

    val scorecard: ScorecardData,
    val gamesByNflTeam: Map<String, ScheduledGameData>,
    val gameAbbrev: String
)

data class ScorecardData (
    val generatedTime: Int,
    val teamInfo: ScorecardTeamInfo,
    val oppInfo: ScorecardTeamInfo?,
    val teamScorecard: TeamScorecard,
    var oppScorecard: TeamScorecard?,
    val weekNum: Int?,
    val matchupTitle: String?
)

data class TeamScorecard (
    val teamSubTotal: Double = 0.0,
    val teamGrandTotal: Double = 0.0,
    val slots: Map<String, ScorecardSlot>? = emptyMap(),
    val modifiers: List<String>? = emptyList(),
    val doubleDown: DoubleDown? = null,
    val spells: TeamSpellInfo? = null,
    val pickEm: PickEmData? = null,
    val pennant: PennantData? = null,
    val topDogSlot: String? = "",
    val unsungHero: UnsungHeroForTeam? = null,
    val special: WeeklySpecialData? = null,

    val tiebreak: Int? = -1,
    val tiebreakType: String? = "",
    val tiebreakAmount: Int? = -1,
    val teamBingoPoints: Int? = -1
)

data class ScorecardSlot (
    val playerInfo: ScorecardPlayerInfo?,
    val stat: ScorecardStat
)

data class ScorecardPlayerInfo (
    val id: Int = -1,
    val pos: String? = "",
    val bye: Int = -1,
    val team: String? = "",
    val team_city: String? = "",
    val team_name: String? = "",
    val jerseyNum: Int? = -1,
    val fullName: String = "",
    val shortName: String = ""
)

data class ScorecardStat (
    val playerSubTotal: Double = 0.0,
    val playerGrandTotal: Double = 0.0,
    val checksum: Long = 0,
    val unsungHero: Boolean? = false,
    val statSet: Map<String, StatRecord>? = emptyMap(),
    val modifiers: List<String>? = emptyList()
)

data class StatRecord (
    val name: String,
    val brief: String,
    val value: Double,
    val total: Double,
    val base: Int,
    val amount: Int,
    val originalAmount: Int?,
    val modifiers: List<String>?
)

data class ScorecardTeamInfo (
    val team_id :Int = -1,
    val league_id :Int = -1,
    val division_num :Int = -1,
    val team_num :Int = -1,
    val team_city :String = "",
    val team_name :String = "",
    val team_abbrev :String = "",
    val team_status :String = "",
    val owner_user_id :Int? = -1,
    val team_type :String = "",
    val creation_date :String = "",
    val modified_date :String = "",
    val curr_wins :Int = -1,
    val curr_losses :Int = -1,
    val curr_pts_for :String = "",
    val curr_pts_against :String = "",
    val eliminated :Int = -1,
    val game_data_json :String? = "",
    val postseason_seed :Int = -1,
    val championship_winner :Int = -1,
    val po_seed_1 :Int = -1,
    val po_seed_2 :Int = -1,
    val avatar_key :String? = "",
    val competition_type :String? = "",
    val scoring_type :String = "",
    val league_name: String = "",
    val league_type :String = "",
    val league_status :String = "",
    val draft_status :String = "",
    val draft_date :String? = "",
    val league_game_data_json :String? = ""
)

data class ModifierType (
    val type: String = "",
    val math: String? = "",
    val stat: String = "",
    val slot: String = "",
    val amount: Double = 0.0
)

// Game Mode types

data class TeamSpellInfo (
    val name: String,
    val image: String?,
    val detail: String,
    val modifier: List<ModifierType>
)

data class PickEmData (
    val gamesPresent: Int,
    val gamesCorrect: Int,
    val score: Double,
    val matchUps: Map<String, PickEmMatchup>
)

data class DoubleDown (
    val playerId: Int?,
    val points: Double
)

data class PickEmMatchup (
    val home: String,
    val away: String,
    val selection: String,
    val correct: Boolean,
    val final: Boolean
)

data class PennantData (
    val color: String = "",
    val name: String = "",
    val type: String = "",
    val image: String = "",
    val detail: String = "",
    val key: String = "",
    val modifier: ModifierType = ModifierType()
)

data class UnsungHeroForTeam (
    val score: Double,
    val data: ScorecardSlot
)

data class WeeklySpecialData (
    val name: String,
    val image: String,
    val detail: String,
    val modifier: List<ModifierType>
)