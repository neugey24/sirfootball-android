package com.sirfootball.android

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.ui.root.LockerRoomPage
import com.sirfootball.android.ui.root.StubPage


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainPage() {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        SFBottomNavItem.LockerRoom,
        SFBottomNavItem.Draft,
        SFBottomNavItem.Add,
        SFBottomNavItem.Questions,
        SFBottomNavItem.Settings
    )

    Scaffold(
        bottomBar = {
            SFBottomNavigation(navController, bottomNavigationItems)
        },
    )
    {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = SFBottomNavItem.LockerRoom.route) {
        composable(SFBottomNavItem.LockerRoom.route) {
            LockerRoomPage()
        }
        composable(SFBottomNavItem.Draft.route) {
            StubPage(titleIn = SFBottomNavItem.Draft.title)
        }
        composable(SFBottomNavItem.Add.route) {
            StubPage(titleIn = SFBottomNavItem.Add.title)
        }
        composable(SFBottomNavItem.Questions.route) {
            StubPage(titleIn = SFBottomNavItem.Questions.title)
        }
        composable(SFBottomNavItem.Settings.route) {
            StubPage(titleIn = SFBottomNavItem.Settings.title)
        }
    }
}



@Composable
private fun SFBottomNavigation(
    navController: NavHostController,
    items: List<SFBottomNavItem>
) {
    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title, fontSize = 10.sp) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = true,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}