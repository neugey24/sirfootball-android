package com.sirfootball.android.structure

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class SirAvatar private constructor() {

    companion object {

        @Composable
        fun getResourceIdForAvatar(avatarKey: String, teamNum: Int) : Int {
            val safeTeamNum = if (teamNum > 12) teamNum.toString().first().digitToInt() else teamNum
            var avatarPath : String
            if (avatarKey == "user_default") {
                avatarPath = "avatar_system_user_default_avatar_${safeTeamNum}"
            } else {
                if (avatarKey == "robo_default") {
                    avatarPath = "avatar_system_robo_default_avatar_${safeTeamNum}"
                } else {
                    val pathTranslate = avatarKey.replace(":", "_")
                    avatarPath = "avatar_${pathTranslate}"
                }
            }

            return LocalContext.current.resources.getIdentifier(avatarPath, "drawable",
                LocalContext.current.packageName)
        }

    }


}