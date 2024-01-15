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

        const val LEAGUE_HOME = "league/$ARG_TAG_LEAGUE_ID"

        const val ARG_WEEK_NUM = "weekNum"
        const val ARG_TAG_WEEK_NUM = "{$ARG_WEEK_NUM}"

        const val ARG_MATCHUP_NUM = "matchupNum"
        const val ARG_TAG_MATCHUP_NUM = "{$ARG_MATCHUP_NUM}"

        const val PREVIEW = "preview/$ARG_TAG_LEAGUE_ID/$ARG_TAG_WEEK_NUM/$ARG_TAG_MATCHUP_NUM"

        const val SCORECARD = "scorecard/$ARG_TAG_LEAGUE_ID/$ARG_TAG_WEEK_NUM/$ARG_TAG_MATCHUP_NUM"

        const val TEAM_ROSTER = "roster/$ARG_TAG_TEAM_ID"

        const val PLAYER_INFO = "player/$ARG_TAG_PLAYER_ID"

    }
}