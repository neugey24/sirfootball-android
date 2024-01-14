package com.sirfootball.android.structure

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.sirfootball.android.R
import com.sirfootball.android.data.model.ScheduledGameData

class SirRoster private constructor() {

    companion object {

        @Composable
        fun getNflGameInfo(teamAbbrevIn : String, gameData : ScheduledGameData?) : String {
            if (gameData == null) {
                return "Bye"
            }

            return if (teamAbbrevIn == gameData.home_team_abbrev) {
                "vs " + gameData.away_team_abbrev
            } else {
                "at " + gameData.home_team_abbrev
            }
        }

        @Composable
        fun getNflGameProgress(gameData : ScheduledGameData?) : String {
            if (gameData == null) {
                return "BYE"
            }
            if (gameData.game_status == "Scheduled") {
                return "SCHED"
            }
            if (gameData.game_status == "Complete") {
                return "DONE"
            }
            return "LIVE"
        }

        @Composable
        fun getStartingSlots() : List<String> {
            return listOf("QB1", "RB1", "RB2", "WR1", "WR2", "TE1", "FLEX", "K1", "DFST1")
        }

        @Composable
        fun getSlotColor(slotIn : String) : Color {
            val slotToColor = mapOf("QB1" to colorResource(R.color.qb_tab_bg),
                    "RB1" to colorResource(R.color.rb_tab_bg),
                    "RB2" to colorResource(R.color.rb_tab_bg),
                    "WR1" to colorResource(R.color.wr_tab_bg),
                    "WR2" to colorResource(R.color.wr_tab_bg),
                    "TE1" to colorResource(R.color.te_tab_bg),
                    "FLEX" to colorResource(R.color.flex_tab_bg),
                    "K1" to colorResource(R.color.k_tab_bg),
                    "DFST1" to colorResource(R.color.dfst_tab_bg),
                    "B1" to colorResource(R.color.score_bench_bg),
                    "B2" to colorResource(R.color.score_bench_bg),
                    "B3" to colorResource(R.color.score_bench_bg),
                    "B4" to colorResource(R.color.score_bench_bg),
                    "B5" to colorResource(R.color.score_bench_bg),
                    "B6" to colorResource(R.color.score_bench_bg)
                )

            return slotToColor[slotIn] ?: Color.White
        }

    }


}