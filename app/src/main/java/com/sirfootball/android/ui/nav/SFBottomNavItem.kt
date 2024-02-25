package com.sirfootball.android.ui.nav

import com.sirfootball.android.R

sealed class SFBottomNavItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    object LockerRoom :
        SFBottomNavItem(
            "lockerRoom",
            "Locker Room",
            R.drawable.ic_home_black_24dp
        )

    object Draft :
        SFBottomNavItem(
            "draft",
            "Draft",
            R.drawable.ic_swap_calls_black_24dp
        )

    object Add :
        SFBottomNavItem(
            "add",
            "Add",
            R.drawable.ic_add_black_24dp
        )

    object Questions :
        SFBottomNavItem(
            "questions",
            "Questions",
            R.drawable.ic_help_center_black_24dp
        )

    object Settings :
        SFBottomNavItem(
            "settings",
            "Settings",
            R.drawable.ic_settings_black_24dp
        )
}