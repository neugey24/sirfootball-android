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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sirfootball.android.ui.add.AddForGamePage
import com.sirfootball.android.ui.nav.AddRoutes
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.ui.root.AddTeamPage
import com.sirfootball.android.ui.root.LockerRoomPage
import com.sirfootball.android.ui.root.StubPage
import com.sirfootball.android.ui.team.LeagueHomePage
import com.sirfootball.android.ui.team.TeamHomePage


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
            LockerRoomPage(navController)
        }
        composable(SFBottomNavItem.Draft.route) {
            StubPage(titleIn = SFBottomNavItem.Draft.title)
        }
        composable(SFBottomNavItem.Add.route) {
            AddTeamPage(navController)
        }
        composable(SFBottomNavItem.Questions.route) {
            StubPage(titleIn = SFBottomNavItem.Questions.title)
        }
        composable(SFBottomNavItem.Settings.route) {
            StubPage(titleIn = SFBottomNavItem.Settings.title)
        }

        navigation(startDestination = SFBottomNavItem.Add.route, route = "addStub") {
            composable(route = SFBottomNavItem.Add.route) {
                AddTeamPage(navController)
            }
            composable(route = AddRoutes.LEAGUES_BY_GAME, arguments = listOf(
                navArgument(AddRoutes.ARG_GAME_ABBREV) { type = NavType.StringType})) {
                val gameAbbrevArgIn = it.arguments?.getString(AddRoutes.ARG_GAME_ABBREV) ?: "UNK"
                AddForGamePage(navController = navController, gameAbbrev = gameAbbrevArgIn)
            }
        }

        navigation(startDestination = SFBottomNavItem.LockerRoom.route, route = "lockerStub") {
            composable(route = SFBottomNavItem.LockerRoom.route) {
                LockerRoomPage(navController)
            }
            composable(route = TeamRoutes.TEAM_HOME, arguments = listOf(
                navArgument(TeamRoutes.ARG_TEAM_ID) { type = NavType.IntType})) {
                val teamIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_TEAM_ID) ?: -1
                TeamHomePage(navController = navController, teamId = teamIdArgIn)
            }

            navigation(startDestination = TeamRoutes.TEAM_HOME, route = "teamStub") {
                composable(route = TeamRoutes.TEAM_HOME, arguments = listOf(
                    navArgument(TeamRoutes.ARG_TEAM_ID) { type = NavType.IntType})) {
                    val teamIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_TEAM_ID) ?: -1
                    TeamHomePage(navController = navController, teamId = teamIdArgIn)
                }
                composable(route = TeamRoutes.LEAGUE_HOME, arguments = listOf(
                    navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                    val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                    LeagueHomePage(navController = navController, leagueId = leagueIdArgIn)
                }

            }

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