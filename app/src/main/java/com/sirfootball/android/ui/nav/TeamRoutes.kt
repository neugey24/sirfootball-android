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

        const val ARG_WEEK_NUM = "weekNum"
        const val ARG_TAG_WEEK_NUM = "{$ARG_WEEK_NUM}"

        const val ARG_MATCHUP_NUM = "matchupNum"
        const val ARG_TAG_MATCHUP_NUM = "{$ARG_MATCHUP_NUM}"

        const val ARG_AVATAR_GROUP = "avatarGroup"
        const val ARG_TAG_AVATAR_GROUP = "{$ARG_AVATAR_GROUP}"

        const val PREVIEW = "preview/$ARG_TAG_LEAGUE_ID/$ARG_TAG_WEEK_NUM/$ARG_TAG_MATCHUP_NUM"

        const val SCORECARD = "scorecard/$ARG_TAG_LEAGUE_ID/$ARG_TAG_WEEK_NUM/$ARG_TAG_MATCHUP_NUM"

        const val TEAM_ROSTER = "roster/$ARG_TAG_TEAM_ID"
        const val TEAM_ROSTER_ADD = "rosterAdd/$ARG_TAG_TEAM_ID/$ARG_TAG_SLOT_NAME/$ARG_TAG_IS_DR/$ARG_TAG_REQUEST_POS"

        const val TEAM_ROSTER_ADD_SLOT = "rosterAddSlot/$ARG_TAG_TEAM_ID/$ARG_TAG_PLAYER_ID/$ARG_TAG_ON_WAIVERS"

        const val TEAM_TRANSACTIONS = "transactions/$ARG_TAG_TEAM_ID"
        const val TEAM_SETTINGS = "settings/$ARG_TAG_TEAM_ID"
        const val TEAM_CHANGE_AVATAR_SELECT_GROUP = "avatarGroup/$ARG_TAG_TEAM_ID"
        const val TEAM_CHANGE_AVATAR_SELECT_AVATAR = "avatarImage/$ARG_TAG_TEAM_ID/$ARG_TAG_AVATAR_GROUP"

        const val TEAM_SCHEDULE = "schedule/$ARG_TAG_TEAM_ID"

        const val PLAYER_INFO = "player/$ARG_TAG_PLAYER_ID"

        const val TEAM_BC_SPELLS = "game/BC/$ARG_TAG_TEAM_ID"
        const val TEAM_DD1_PICKS = "game/DD1/$ARG_TAG_TEAM_ID"
        const val TEAM_DD2_PICKS = "game/DD2/$ARG_TAG_TEAM_ID"
        const val TEAM_PP_SELECTIONS = "game/PP/$ARG_TAG_TEAM_ID"
        const val TEAM_WS_SHOW = "game/WS/$ARG_TAG_TEAM_ID"

        const val LEAGUE_HOME = "league/$ARG_TAG_LEAGUE_ID"
        const val LEAGUE_STANDINGS = "league/standings/$ARG_TAG_LEAGUE_ID"
        const val LEAGUE_HIGH_SCORES = "league/highScores/$ARG_TAG_LEAGUE_ID"
        const val LEAGUE_SCOREBOARD = "league/scoreboard/$ARG_TAG_LEAGUE_ID"
        const val LEAGUE_TRANSACTIONS = "league/transactions/$ARG_TAG_LEAGUE_ID"

    }
}