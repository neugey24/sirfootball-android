package com.sirfootball.android.ui.nav

class TeamRoutes private constructor() {

    companion object {
        const val ARG_TEAM_ID = "teamId"
        const val ARG_TAG_TEAM_ID = "{$ARG_TEAM_ID}"

        const val TEAM_HOME = "team/$ARG_TAG_TEAM_ID"

        const val ARG_LEAGUE_ID = "leagueId"
        const val ARG_TAG_LEAGUE_ID= "{$ARG_LEAGUE_ID}"

        const val LEAGUE_HOME = "league/$ARG_TAG_LEAGUE_ID"

    }

}