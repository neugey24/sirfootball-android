package com.sirfootball.android.structure

class SirGame private constructor() {

    companion object {
        private val BB_DATA = mapOf("abbrev" to "BB", "name"  to  "Barstool Bingo", "style"  to  "barstoolBingo", "image"  to  "barstool_bingo",
            "logo" to "/images/bingo/bb.png", "gamePage" to "barstool_bingo", "scoreDecimalPlaces" to 0, "knightName" to "Bedivere")

        private val BC_DATA = mapOf(  "abbrev" to "BC", "name"  to  "Blessed and Cursed", "style"  to  "blessedCursed", "image"  to  "blessed_cursed",
            "logo" to "bc2.png", "gamePage" to "blessed_and_cursed", "scoreDecimalPlaces" to 2, "knightName" to "Caradoc")

        private val DG_DATA = mapOf(   "abbrev" to "DG", "name"  to  "Decathlon Gold", "style"  to  "decathlonGold", "image"  to  "decathlon_gold",
            "logo" to "dg.png", "gamePage" to "decathlon_gold", "scoreDecimalPlaces" to 0, "knightName" to "Lancelot")

        private val DD1_DATA = mapOf(
            "abbrev" to "DD1", "name"  to  "Decisions, Decisions", "style"  to  "decDec", "image"  to  "decdec",
            "logo" to "dd1.png", "gamePage" to "decisions_decisions", "scoreDecimalPlaces" to 2, "knightName" to "Claudin")

        private val DD2_DATA = mapOf(
            "abbrev" to "DD2", "name"  to  "Double Down", "style"  to  "doubleDown", "image"  to  "doubledown",
            "logo" to "dd2.png", "gamePage" to "double_down", "scoreDecimalPlaces" to 2, "knightName" to "Cador")

        private val GV_DATA = mapOf(
            "abbrev" to "GV", "name"  to  "Garden Variety", "style"  to  "gardenVariety", "image"  to  "garden_variety",
            "logo" to "gv.png", "gamePage" to "garden_variety", "scoreDecimalPlaces" to 2, "knightName" to "Gawain")

        private val PD_DATA = mapOf(
            "abbrev" to "PD", "name"  to  "Paydirt", "style"  to  "paydirt", "image"  to  "paydirt",
            "logo" to "pd.png", "gamePage" to "paydirt", "scoreDecimalPlaces" to 0, "knightName" to "Morholt")

        private val PP_DATA = mapOf(
            "abbrev" to "PP", "name"  to  "Pennant Play", "style"  to  "pennantPlay", "image"  to  "pennant_play",
            "logo" to "pp2.png", "gamePage" to "pennant_play", "scoreDecimalPlaces" to 2, "knightName" to "Percival")

        private val SL_DATA = mapOf(
            "abbrev" to "SL", "name"  to  "Squid League", "style"  to  "squid", "image"  to  "squidleague",
            "logo" to "sl.png", "gamePage" to "squid_league", "scoreDecimalPlaces" to 2, "knightName" to "Sagramor")

        private val SS_DATA = mapOf(
            "abbrev" to "SS", "name"  to  "Scary Sight", "style"  to  "scarySight", "image"  to  "scarysight",
            "logo" to "ss2.png", "gamePage" to "scary_sight", "scoreDecimalPlaces" to 2, "knightName" to "Modred")

        private val TD_DATA = mapOf(
            "abbrev" to "TD", "name"  to  "Top Dog", "style"  to  "topDog", "image"  to  "topdog",
            "logo" to "td.png", "gamePage" to "top_dog", "scoreDecimalPlaces" to 2, "knightName" to "Tristan")

        private val UH_DATA = mapOf(
            "abbrev" to "UH", "name"  to  "Unsung Hero", "style"  to  "unsungHero", "image"  to  "unsung_hero",
            "logo" to "uh.png", "gamePage" to "unsung_hero", "scoreDecimalPlaces" to 2, "knightName" to "Safir")

        private val WS_DATA = mapOf(
            "abbrev" to "WS", "name"  to  "Weekly Special", "style"  to  "weeklySpecial", "image"  to  "weeklyspecial",
            "logo" to "ws.png", "gamePage" to "weekly_special", "scoreDecimalPlaces" to 2, "knightName" to "Yvain")

        val GAME_DATA = mapOf("BB" to BB_DATA, "BC" to BC_DATA, "DG" to DG_DATA,  "DD1" to DD1_DATA, "DD2" to DD2_DATA,
            "GV" to GV_DATA, "PD" to PD_DATA, "PP" to PP_DATA, "SL" to SL_DATA, "SS" to SS_DATA, "TD" to TD_DATA,
            "UH" to UH_DATA, "WS" to WS_DATA )


    }

}