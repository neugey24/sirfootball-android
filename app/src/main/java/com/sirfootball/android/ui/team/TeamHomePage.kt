package com.sirfootball.android.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.sirfootball.android.viewmodel.GetTeamInfoViewModel


@Composable
fun TeamHomePage(navController: NavHostController, teamId : Int) {
    val viewModel = hiltViewModel<GetTeamInfoViewModel>()
    val loadState = viewModel.getTeamInfoResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text(" ", fontSize = 70.sp)
            Text("Loading Data ...", fontSize = 30.sp)
        }

        is ApiState.Success -> {
            val responseData = loadState.data
            val teamInfo = responseData.teamInfo
            val gameLogoId = LocalContext.current.resources.getIdentifier(
                "game_logo_${SirGame.GAME_DATA[teamInfo.game_abbrev]?.get("logo")}", "drawable",
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
                    Text(text = "${SirGame.GAME_DATA[teamInfo.game_abbrev]?.get("name")}", fontSize = 24.sp)
                    Text(text = "${teamInfo.team_city} ${teamInfo.team_name} (${teamInfo.team_abbrev})", fontSize = 16.sp)
                    Text(text = "Record: ${teamInfo.curr_wins}-${teamInfo.curr_losses}", fontSize = 17.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Team Tools & Options:", fontSize = 17.sp, fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(vertical = 8.dp))
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {
                        navController.navigate(
                            TeamRoutes.TEAM_ROSTER.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString())
                        )
                    }
                ) {
                    Text("Manage Team Roster", fontSize = 16.sp)
                }
                if (teamInfo.game_abbrev == "DD1") {
                    Text(" ", fontSize = 4.sp)
                    Button(contentPadding = PaddingValues(all = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                            contentColor = Color.White),
                        modifier = Modifier.size(width = 360.dp, height = 34.dp),
                        onClick = {
                            navController.navigate(
                                TeamRoutes.TEAM_DD1_PICKS.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString())
                            )
                        }
                    ) {
                        Text("Weekly Pick'Em", fontSize = 16.sp)
                    }
                }
                if (teamInfo.game_abbrev == "DD2") {
                    Text(" ", fontSize = 4.sp)
                    Button(contentPadding = PaddingValues(all = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                            contentColor = Color.White),
                        modifier = Modifier.size(width = 360.dp, height = 34.dp),
                        onClick = {
                            navController.navigate(
                                TeamRoutes.TEAM_DD2_PICKS.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString())
                            )
                        }
                    ) {
                        Text("Double Down Selection", fontSize = 16.sp)
                    }
                }
                if (teamInfo.game_abbrev == "PP") {
                    Text(" ", fontSize = 4.sp)
                    Button(contentPadding = PaddingValues(all = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                            contentColor = Color.White),
                        modifier = Modifier.size(width = 360.dp, height = 34.dp),
                        onClick = {
                            navController.navigate(
                                TeamRoutes.TEAM_PP_SELECTIONS.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString())
                            )
                        }
                    ) {
                        Text("Pennant Selection", fontSize = 16.sp)
                    }
                }
                if (teamInfo.game_abbrev == "WS") {
                    Text(" ", fontSize = 4.sp)
                    Button(contentPadding = PaddingValues(all = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                            contentColor = Color.White),
                        modifier = Modifier.size(width = 360.dp, height = 34.dp),
                        onClick = {
                            navController.navigate(
                                TeamRoutes.TEAM_WS_SHOW.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString())
                            )
                        }
                    ) {
                        Text("Weekly Special", fontSize = 16.sp)
                    }
                }
                if (teamInfo.game_abbrev == "BC") {
                    Text(" ", fontSize = 4.sp)
                    Button(contentPadding = PaddingValues(all = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                            contentColor = Color.White),
                        modifier = Modifier.size(width = 360.dp, height = 34.dp),
                        onClick = {
                            navController.navigate(
                                TeamRoutes.TEAM_BC_SPELLS.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString())
                            )
                        }
                    ) {
                        Text("Spell Selection", fontSize = 16.sp)
                    }
                }
                Text(" ", fontSize = 4.sp)
                if (responseData.weekNum > 0) {
                    if (responseData.gameLive) {
                        // Show scorecard
                        Button(contentPadding = PaddingValues(all = 2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                                contentColor = Color.White),
                            modifier = Modifier.size(width = 360.dp, height = 34.dp),
                            onClick = {
                                navController.navigate(
                                    TeamRoutes.SCORECARD.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, teamInfo.league_id.toString())
                                        .replace(TeamRoutes.ARG_TAG_WEEK_NUM, responseData.weekNum.toString())
                                        .replace(TeamRoutes.ARG_TAG_MATCHUP_NUM, responseData.matchupNum.toString())
                                )
                            }
                        ) {
                            Text("Matchup: ${responseData.matchupSummary}", fontSize = 16.sp)
                        }
                    } else {
                        // Show matchup preview
                        Button(contentPadding = PaddingValues(all = 2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                                contentColor = Color.White),
                            modifier = Modifier.size(width = 360.dp, height = 34.dp),
                            onClick = {
                                navController.navigate(
                                    TeamRoutes.PREVIEW.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, teamInfo.league_id.toString())
                                        .replace(TeamRoutes.ARG_TAG_WEEK_NUM, responseData.weekNum.toString())
                                        .replace(TeamRoutes.ARG_TAG_MATCHUP_NUM, responseData.matchupNum.toString())
                                )
                            }
                        ) {
                            Text("Matchup: ${responseData.matchupSummary}", fontSize = 16.sp)
                        }
                    }

                } // end > week 0
                Text(" ", fontSize = 4.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 360.dp, height = 34.dp),
                    onClick = {
                        navController.navigate(
                            TeamRoutes.LEAGUE_HOME.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, teamInfo.league_id.toString()))
                    }
                ) {
                    Text("League: ${teamInfo.league_name}", fontSize = 16.sp)
                }
                Text(" ", fontSize = 8.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 220.dp, height = 34.dp),
                    onClick = {
                    }
                ) {
                    Text("Team Settings", fontSize = 16.sp)
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                    Image(
                        painter = painterResource(
                            id = SirAvatar.getResourceIdForAvatar(
                                teamInfo.avatar_key,
                                teamInfo.team_num
                            )
                        ),
                        contentDescription = "Team Avatar",
                        modifier = Modifier.size(100.dp)
                    )
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Team Avatar", fontSize = 22.sp)
                        Button(contentPadding = PaddingValues(all = 3.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                                contentColor = Color.White),
                            modifier = Modifier.size(width = 160.dp, height = 34.dp),
                            onClick = {
                            }
                        ) {
                            Text("Change Avatar", fontSize = 15.sp)
                        }
                    }

                }
            }

        }

        else -> {
            val error = loadState.toString()
            Text("Error loading team Occurred: $error")
        }
    }

}
