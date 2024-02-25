package com.sirfootball.android.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.GetLeagueInfoViewModel
import com.sirfootball.android.viewmodel.GetTeamInfoViewModel


@Composable
fun LeagueHomePage(navController: NavHostController, leagueId : Int) {
    val viewModel = hiltViewModel<GetLeagueInfoViewModel>()
    val loadState = viewModel.getLeagueInfoResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(leagueId)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text("Loading Data ...")
        }

        is ApiState.Success -> {
            val responseData = loadState.data
            val leagueInfo = responseData.leagueInfo
            val gameLogoId = LocalContext.current.resources.getIdentifier(
                "game_logo_${SirGame.GAME_DATA[leagueInfo.game_abbrev]?.get("logo")}", "drawable",
                LocalContext.current.packageName)

            val padTop = Modifier.padding(top = 25.dp)
            Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                Image(
                    painter = painterResource( id = gameLogoId),
                    contentDescription = "Game Type",
                    modifier = Modifier.size(100.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${SirGame.GAME_DATA[leagueInfo.game_abbrev]?.get("name")}", fontSize = 24.sp)
                    Text(text = leagueInfo.league_name, fontSize = 16.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "League Info & Options:", fontSize = 17.sp, fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(vertical = 8.dp))
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {
                        navController.navigate(
                            TeamRoutes.LEAGUE_STANDINGS.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, leagueId.toString())
                        )
                    }
                ) {
                    Text("League Standings", fontSize = 16.sp)
                }
                if (responseData.gamesCommenced) {
                    Text(" ", fontSize = 8.sp)
                    Button(contentPadding = PaddingValues(all = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                            contentColor = Color.White),
                        modifier = Modifier.size(width = 360.dp, height = 34.dp),
                        onClick = {
                            navController.navigate(
                                TeamRoutes.LEAGUE_SCOREBOARD.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, leagueId.toString())
                            )
                        }
                    ) {
                        Text("League Scoreboard", fontSize = 16.sp)
                    }
                }
                Text(" ", fontSize = 8.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {
                        navController.navigate(
                            TeamRoutes.LEAGUE_TRANSACTIONS.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, leagueId.toString())
                        )
                    }
                ) {
                    Text("League Transactions", fontSize = 16.sp)
                }
                Text(" ", fontSize = 8.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {
                        navController.navigate(
                            TeamRoutes.LEAGUE_HIGH_SCORES.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, leagueId.toString())
                        )
                    }
                ) {
                    Text("League High Scores", fontSize = 16.sp)
                }
                Text(" ", fontSize = 8.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {

                    }
                ) {
                    Text("Game Rules", fontSize = 16.sp)
                }
                Text(" ", fontSize = 8.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text("Return to Your Team", fontSize = 16.sp)
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Competition: ${leagueInfo.competition_type}", fontSize = 16.sp)
                Text("Scoring: ${leagueInfo.scoring_type}", fontSize = 16.sp)
                Text("League Status: ${leagueInfo.league_status}", fontSize = 16.sp)
                Text("Draft Status: ${leagueInfo.draft_status}", fontSize = 16.sp)
                if (leagueInfo.draft_status == "Scheduled") {
                    Text("Draft Date: ${leagueInfo.draft_date}", fontSize = 16.sp)
                }

            }

        }

        else -> {
            val error = loadState.toString()
            Text("Error loading league occurred: $error")
        }
    }

}
