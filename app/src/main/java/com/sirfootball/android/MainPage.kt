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
import com.sirfootball.android.ui.team.PreviewPage
import com.sirfootball.android.ui.team.ScorecardPage
import com.sirfootball.android.ui.team.TeamHomePage
import com.sirfootball.android.ui.team.TeamRosterPage
import com.sirfootball.android.ui.team.detail.PlayerInfoPage


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
                composable(route = TeamRoutes.PREVIEW, arguments = listOf(
                    navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType},
                    navArgument(TeamRoutes.ARG_WEEK_NUM) { type = NavType.IntType},
                    navArgument(TeamRoutes.ARG_MATCHUP_NUM) { type = NavType.IntType})) {
                    val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                    val weekArgIn = it.arguments?.getInt(TeamRoutes.ARG_WEEK_NUM) ?: -1
                    val matchupArgIn = it.arguments?.getInt(TeamRoutes.ARG_MATCHUP_NUM) ?: -1
                    PreviewPage(leagueId = leagueIdArgIn,
                        weekNum = weekArgIn, matchupNum = matchupArgIn)
                }
                composable(route = TeamRoutes.SCORECARD, arguments = listOf(
                    navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType},
                    navArgument(TeamRoutes.ARG_WEEK_NUM) { type = NavType.IntType},
                    navArgument(TeamRoutes.ARG_MATCHUP_NUM) { type = NavType.IntType})) {
                    val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                    val weekArgIn = it.arguments?.getInt(TeamRoutes.ARG_WEEK_NUM) ?: -1
                    val matchupArgIn = it.arguments?.getInt(TeamRoutes.ARG_MATCHUP_NUM) ?: -1
                    ScorecardPage(leagueId = leagueIdArgIn,
                        weekNum = weekArgIn, matchupNum = matchupArgIn)
                }
                composable(route = TeamRoutes.TEAM_ROSTER, arguments = listOf(
                    navArgument(TeamRoutes.ARG_TEAM_ID) { type = NavType.IntType})) {
                    val teamIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_TEAM_ID) ?: -1
                    TeamRosterPage(navController = navController, teamId = teamIdArgIn)
                }

                navigation(startDestination = TeamRoutes.TEAM_ROSTER, route = "teamRosterStub") {
                    composable(route = TeamRoutes.TEAM_ROSTER, arguments = listOf(
                        navArgument(TeamRoutes.ARG_TEAM_ID) { type = NavType.IntType})) {
                        val teamIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_TEAM_ID) ?: -1
                        TeamRosterPage(navController = navController, teamId = teamIdArgIn)
                    }

                    composable(route = TeamRoutes.PLAYER_INFO, arguments = listOf(
                        navArgument(TeamRoutes.ARG_PLAYER_ID) { type = NavType.IntType})) {
                        val playerIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_PLAYER_ID) ?: -1
                        PlayerInfoPage(playerId = playerIdArgIn)
                    }
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