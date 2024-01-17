package com.sirfootball.android.ui.nav

class TeamRoutes private constructor() {

    companion object {
        const val ARG_TEAM_ID = "teamId"
        const val ARG_TAG_TEAM_ID = "{$ARG_TEAM_ID}"

        const val TEAM_HOME = "team/$ARG_TAG_TEAM_ID"

        const val ARG_LEAGUE_ID = "leagueId"
        const val ARG_TAG_LEAGUE_ID = "{$ARG_LEAGUE_ID}"

        const val ARG_PLAYER_ID = "playerId"
        const val ARG_TAG_PLAYER_ID = "{$ARG_PLAYER_ID}"

        const val ARG_SLOT_NAME = "slotName"
        const val ARG_TAG_SLOT_NAME = "{$ARG_SLOT_NAME}"

        const val ARG_IS_DR = "isDr"
        const val ARG_TAG_IS_DR = "{$ARG_IS_DR}"

        const val ARG_ON_WAIVERS = "onWaivers"
        const val ARG_TAG_ON_WAIVERS = "{$ARG_ON_WAIVERS}"

        const val ARG_REQUEST_POS = "requestPos"
        const val ARG_TAG_REQUEST_POS = "{$ARG_REQUEST_POS}"

        const val LEAGUE_HOME = "league/$ARG_TAG_LEAGUE_ID"

        const val ARG_WEEK_NUM = "weekNum"
        const val ARG_TAG_WEEK_NUM = "{$ARG_WEEK_NUM}"

        const val ARG_MATCHUP_NUM = "matchupNum"
        const val ARG_TAG_MATCHUP_NUM = "{$ARG_MATCHUP_NUM}"

        const val PREVIEW = "preview/$ARG_TAG_LEAGUE_ID/$ARG_TAG_WEEK_NUM/$ARG_TAG_MATCHUP_NUM"

        const val SCORECARD = "scorecard/$ARG_TAG_LEAGUE_ID/$ARG_TAG_WEEK_NUM/$ARG_TAG_MATCHUP_NUM"

        const val TEAM_ROSTER = "roster/$ARG_TAG_TEAM_ID"
        const val TEAM_ROSTER_ADD = "rosterAdd/$ARG_TAG_TEAM_ID/$ARG_TAG_SLOT_NAME/$ARG_TAG_IS_DR/$ARG_TAG_REQUEST_POS"

        const val TEAM_ROSTER_ADD_SLOT = "rosterAddSlot/$ARG_TAG_TEAM_ID/$ARG_TAG_PLAYER_ID/$ARG_TAG_ON_WAIVERS"

        const val PLAYER_INFO = "player/$ARG_TAG_PLAYER_ID"

    }
}