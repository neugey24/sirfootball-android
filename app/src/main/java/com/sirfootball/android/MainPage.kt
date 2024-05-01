package com.sirfootball.android

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.LoginFormData
import com.sirfootball.android.ui.nav.AddRoutes
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.ui.nav.UserRoutes
import com.sirfootball.android.ui.root.LockerRoomPage
import com.sirfootball.android.ui.root.StubPage
import com.sirfootball.android.ui.root.UserCrownsPage
import com.sirfootball.android.ui.team.LeagueHomePage
import com.sirfootball.android.ui.team.PreviewPage
import com.sirfootball.android.ui.team.ScorecardPage
import com.sirfootball.android.ui.team.TeamHomePage
import com.sirfootball.android.ui.team.TeamSchedulePage
import com.sirfootball.android.ui.team.TeamTransactionsPage
import com.sirfootball.android.ui.team.league.LeagueHighScoresPage
import com.sirfootball.android.ui.team.league.LeagueScoreboardPage
import com.sirfootball.android.ui.team.league.LeagueStandingsPage
import com.sirfootball.android.ui.team.league.LeagueTransactionsPage
import com.sirfootball.android.viewmodel.DataPersistenceViewModel

public const val SF_USER_SESSION = "com.sirfootball.user.session"

public const val SF_USER_SESSION_KEY = "com.sirfootball.user.session.key"
private const val SF_USER_SESSION_USERNAME = "com.sirfootball.user.session.username"

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainPage() {

    var authReload by remember { mutableStateOf(true) }
    val sessionKey = LocalContext.current.getSharedPreferences(SF_USER_SESSION, 0).getString(SF_USER_SESSION_KEY, "NA")

    if (sessionKey == "NA" || authReload) {
            // show login
            SirFootballLogin(authReloadIn = {authReload = it})

    } else {
        // Show authenticated UI
        authReload = false
        val navController = rememberNavController()

        val bottomNavigationItems = listOf(
            SFBottomNavItem.LockerRoom,
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


}

@Composable
fun SirFootballLogin(authReloadIn: (Boolean) -> Unit) {

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.authResponse.value

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var invalidLoginText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {

        Spacer(Modifier.weight(2f))
        Text(text = " Login To Sir Football", fontSize = 28.sp)
        Spacer(Modifier.weight(1f))
        OutlinedTextField(
            modifier = Modifier.padding(2.dp),
            value = username,
            onValueChange = {
                username = it
            },
            label = { Text("Username") }
        )
        OutlinedTextField(
            modifier = Modifier.padding(4.dp),
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text("Password") }
        )
        Spacer(Modifier.weight(1f))
        Button(contentPadding = PaddingValues(all = 2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            modifier = Modifier.size(width = 140.dp, height = 34.dp),
            onClick = {
                val formData = LoginFormData(
                    username = username, password = password)
                saveViewModel.authenticate(formData)
            }
        ) {
            Text("Login", fontSize = 14.sp)
        }
        Spacer(Modifier.weight(1f))
        Text(text = invalidLoginText, fontSize = 18.sp, color = Color.Red)
        Spacer(Modifier.weight(7f))

    }

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state - authentication")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - authentication")

            // Save token to session context
            val sessionDataEditor = LocalContext.current.getSharedPreferences(SF_USER_SESSION, 0).edit()
            sessionDataEditor.putString(SF_USER_SESSION_KEY, saveState.data.token)
            sessionDataEditor.putString(SF_USER_SESSION_USERNAME, saveState.data.username)
            sessionDataEditor.apply()

            authReloadIn(false)

//            navController.navigate(
//                TeamRoutes.TEAM_ROSTER.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamId.toString()))
        }

        else -> {
            Log.i("Save", "Authentication failed")
            invalidLoginText = "Invalid Login"
        }
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
        composable(SFBottomNavItem.Settings.route) {
            StubPage()
        }

        navigation(startDestination = SFBottomNavItem.LockerRoom.route, route = "lockerStub") {
            composable(route = SFBottomNavItem.LockerRoom.route) {
                LockerRoomPage(navController)
            }
            composable(route = UserRoutes.CROWN_DISPLAY) {
                UserCrownsPage()
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
                composable(route = TeamRoutes.TEAM_SCHEDULE, arguments = listOf(
                    navArgument(TeamRoutes.ARG_TEAM_ID) { type = NavType.IntType})) {
                    val teamIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_TEAM_ID) ?: -1
                    TeamSchedulePage(navController = navController, teamId = teamIdArgIn)
                }
                composable(route = TeamRoutes.TEAM_TRANSACTIONS, arguments = listOf(
                    navArgument(TeamRoutes.ARG_TEAM_ID) { type = NavType.IntType})) {
                    val teamIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_TEAM_ID) ?: -1
                    TeamTransactionsPage(teamId = teamIdArgIn)
                }

                composable(route = TeamRoutes.LEAGUE_HOME, arguments = listOf(
                    navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                    val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                    LeagueHomePage(navController = navController, leagueId = leagueIdArgIn)
                }

                navigation(startDestination = TeamRoutes.TEAM_ROSTER, route = "leagueHomeStub") {
                    composable(route = TeamRoutes.LEAGUE_HOME, arguments = listOf(
                        navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                        val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                        LeagueHomePage(navController = navController, leagueId = leagueIdArgIn)
                    }
                    composable(route = TeamRoutes.LEAGUE_TRANSACTIONS, arguments = listOf(
                        navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                        val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                        LeagueTransactionsPage(leagueId = leagueIdArgIn)
                    }
                    composable(route = TeamRoutes.LEAGUE_HIGH_SCORES, arguments = listOf(
                        navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                        val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                        LeagueHighScoresPage(leagueId = leagueIdArgIn)
                    }

                    composable(route = TeamRoutes.LEAGUE_STANDINGS, arguments = listOf(
                        navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                        val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                        LeagueStandingsPage(leagueId = leagueIdArgIn)
                    }

                    composable(route = TeamRoutes.LEAGUE_SCOREBOARD, arguments = listOf(
                        navArgument(TeamRoutes.ARG_LEAGUE_ID) { type = NavType.IntType})) {
                        val leagueIdArgIn = it.arguments?.getInt(TeamRoutes.ARG_LEAGUE_ID) ?: -1
                        LeagueScoreboardPage(navController = navController, leagueId = leagueIdArgIn)
                    }
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