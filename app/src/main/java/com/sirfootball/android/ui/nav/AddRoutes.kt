package com.sirfootball.android.ui.nav

class AddRoutes private constructor() {

    companion object {
        const val ARG_GAME_ABBREV = "gameAbbrev"
        const val ARG_TAG_GAME_ABBREV = "{$ARG_GAME_ABBREV}"

        const val LEAGUES_BY_GAME = "addForGame/$ARG_TAG_GAME_ABBREV"

    }

}