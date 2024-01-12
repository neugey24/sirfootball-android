package com.sirfootball.android.data.model

data class LoadScorecardResponse (
    val result: String,
    val detail: String,

    val scorecard: ScorecardData,
    val gamesByNflTeam: Map<String, ScheduledGameData>
)

data class ScorecardData (
    val generatedTime: Int,
    val teamInfo: ScorecardTeamInfo,
    val oppInfo: ScorecardTeamInfo,
    val teamScorecard: TeamScorecard,
    val oppScorecard: TeamScorecard?,
    val weekNum: Int?,
    val matchupTitle: String?
)

data class TeamScorecard (
    val teamSubTotal: Double,
    val teamGrandTotal: Double,
    val slots: Map<String, ScorecardSlot>?,
    val modifiers: String?,
    val doubleDown: DoubleDown?,
    val spells: TeamSpellInfo?,
    val pickEm: PickEmData,
    val pennant: PennantData,
    val topDogSlot: String?,
    val unsungHero: UnsungHeroForTeam?,
    val special: WeeklySpecialData?,

    val tiebreak: Int?,
    val tiebreakType: String?,
    val tiebreakAmount: Int?,
    val teamBingoPoints: Int?
)

data class ScorecardSlot (
    val playerInfo: ScorecardPlayerInfo?,
    val stat: ScorecardStat
)

data class ScorecardPlayerInfo (
    val id: Int,
    val pos: String?,
    val bye: Int,
    val team: String?,
    val team_city: String?,
    val team_name: String?,
    val jerseyNum: Int?,
    val fullName: String,
    val shortName: String
)

data class ScorecardStat (
    val playerSubTotal: Double,
    val playerGrandTotal: Double,
    val checksum: Int,
    val unsungHero: Boolean?,
    val statSet: Map<String, StatRecord>,
    val modifiers: List<String>?
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
    val team_id :Int,
    val league_id :Int,
    val division_num :Int,
    val team_num :Int,
    val team_city :String,
    val team_name :String,
    val team_abbrev :String,
    val team_status :String,
    val owner_user_id :Int?,
    val team_type :String,
    val creation_date :String,
    val modified_date :String,
    val curr_wins :Int,
    val curr_losses :Int,
    val curr_pts_for :String,
    val curr_pts_against :String,
    val eliminated :Int,
    val game_data_json :String?,
    val postseason_seed :Int,
    val championship_winner :Int,
    val po_seed_1 :Int,
    val po_seed_2 :Int,
    val avatar_key :String?,
    val competition_type :String?,
    val scoring_type :String,
    val league_type :String,
    val league_status :String,
    val draft_status :String,
    val draft_date :String?,
    val league_game_data_json :String?
)

data class ModifierType (
    val type: String,
    val math: String?,
    val stat: String,
    val slot: String,
    val amount: Double
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
    val color: String,
    val name: String,
    val type: String,
    val image: String,
    val detail: String,
    val key: String,
    val modifier: ModifierType
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