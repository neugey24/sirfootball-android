package com.sirfootball.android.ui.team.league

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.LoadLeagueScoreboardResponse
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.GetLeagueScoreboardViewModel


@Composable
fun LeagueScoreboardPage(navController: NavHostController, leagueId : Int) {
    val viewModel = hiltViewModel<GetLeagueScoreboardViewModel>()
    val loadState = viewModel.getLeagueScoreboardResponse.value

    var teamCount = 0

    LaunchedEffect(Unit) {
        viewModel.fetch(leagueId)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text("Loading Data ...")
        }

        is ApiState.Success -> {
            val responseData = loadState.data
            val board = responseData.scoreboard
            val info = responseData.info

            val gameLogoId = LocalContext.current.resources.getIdentifier(
                "game_logo_${SirGame.GAME_DATA[info.leagueInfo.game_abbrev]?.get("logo")}", "drawable",
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
                    Text(text = "${SirGame.GAME_DATA[info.leagueInfo.game_abbrev]?.get("name")}", fontSize = 24.sp)
                    Text(text = info.leagueInfo.league_name, fontSize = 16.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Text("NFL Week ${board.weekNum}", fontSize = 34.sp)


                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(458.dp)
                ) {
                    if (board.weekNum > 14) {
                        item {
                            Text("Playoff Scoreboard", fontSize = 28.sp)
                            Divider()
                            RenderScoreGroup(navController,"Playoff", board, info.leagueInfo.game_abbrev, leagueId)
                        }
                        item {

                            Text("Leisure Scoreboard", fontSize = 28.sp)
                            Divider()
                            RenderScoreGroup(navController,"Leisure", board, info.leagueInfo.game_abbrev, leagueId)
                        }
                    } else {
                        item {
                            Text("Regular Season Scoreboard", fontSize = 28.sp)
                            Divider()
                            RenderScoreGroup(navController,"Regular Season", board, info.leagueInfo.game_abbrev, leagueId)
                        }
                    }

                }
            }
        }

        else -> {
            val error = loadState.toString()
            Text("Error loading league standings occurred: $error")
        }
    }

}

@Composable
fun RenderScoreGroup(navController: NavHostController, type: String,
                     board: LoadLeagueScoreboardResponse, gameAbbrev: String, leagueId: Int) {
    for (currMu in board.matchUps) {
        if (currMu.type == type) {
            val currTeam = board.teams[currMu.team.toString()]
            val currOpp = board.teams[currMu.opp.toString()]
            Text(" ", fontSize = 20.sp)
            Row {
                Text("${currTeam?.team_city} ${currTeam?.team_name} ${currTeam?.team_abbrev}",
                    fontSize = 12.sp, modifier = Modifier.width(160.dp), textAlign = TextAlign.Center)
                Text(" ", fontSize = 14.sp, modifier = Modifier.width(40.dp))
                Text("${currOpp?.team_city} ${currOpp?.team_name} ${currOpp?.team_abbrev}",
                    fontSize = 12.sp, modifier = Modifier.width(160.dp), textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.clickable(onClick = {
                navController.navigate(
                    TeamRoutes.SCORECARD.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, leagueId.toString())
                        .replace(TeamRoutes.ARG_TAG_WEEK_NUM, board.weekNum.toString())
                        .replace(TeamRoutes.ARG_TAG_MATCHUP_NUM, currMu.linkNum.toString())
                )
            })) {
                Image(
                    painter = painterResource(
                        id = SirAvatar.getResourceIdForAvatar(
                            currTeam?.avatar_key ?: "UNK",
                            currTeam?.team_num ?: -1
                        )
                    ),
                    contentDescription = "Team Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(start = 40.dp, end = 0.dp)
                )
                Text(" ", fontSize = 14.sp, modifier = Modifier.width(40.dp))
                Image(
                    painter = painterResource(
                        id = SirAvatar.getResourceIdForAvatar(
                            currOpp?.avatar_key ?: "UNK",
                            currOpp?.team_num ?: -1
                        )
                    ),
                    contentDescription = "Team Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(start = 0.dp, end = 40.dp)
                )
            }
            Row {

                Text(SirGame.formatScoreForGame(scoreIn = board.scorecards[currMu.team.toString()], gameAbbrevIn = gameAbbrev),
                    fontSize = 20.sp, textAlign = TextAlign.Center , modifier = Modifier.width(160.dp).background(
                        color = colorResource(
                            R.color.score_team_bg
                        )
                    ))
                Text(" ", fontSize = 14.sp, modifier = Modifier.width(40.dp))
                Text(SirGame.formatScoreForGame(scoreIn = board.scorecards[currMu.opp.toString()], gameAbbrevIn = gameAbbrev),
                    fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.width(160.dp).background(
                        color = colorResource(
                            R.color.score_opp_bg
                        )
                    ))
            }
            Text(" ", fontSize = 20.sp)
            Divider()
        }

    }

}
