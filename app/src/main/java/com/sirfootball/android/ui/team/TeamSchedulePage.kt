package com.sirfootball.android.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.GetTeamInfoResponse
import com.sirfootball.android.data.model.ScheduleInfo
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.GetTeamScheduleViewModel


@Composable
fun TeamSchedulePage(navController: NavHostController, teamId : Int) {
    val viewModel = hiltViewModel<GetTeamScheduleViewModel>()
    val loadState = viewModel.getTeamScheduleResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text("Loading Data ...")
        }

        is ApiState.Success -> {
            val responseData = loadState.data
            val team = responseData.info
            val schedule = responseData.schedule

            val padTop = Modifier.padding(top = 25.dp, start = 15.dp, end = 15.dp)
            Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                Image(
                    painter = painterResource(
                        id = SirAvatar.getResourceIdForAvatar(
                            team.teamInfo.avatar_key,
                            team.teamInfo.team_num
                        )
                    ),
                    contentDescription = "Team Avatar",
                    modifier = Modifier.size(140.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = team.teamInfo.team_city, fontSize = 28.sp)
                    Text(text = team.teamInfo.team_name, fontSize = 28.sp)
                    Text(text = "(${team.teamInfo.team_abbrev})", fontSize = 24.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Team Schedule", fontSize = 28.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(408.dp)
                ) {
                    for (currentGame in schedule.schedule) {
                        item {
                            if (team.teamInfo.competition_type == "H2H") {
                                RenderH2HWeek(navController, currentGame, team)
                            } else {
                                RenderSurvivorWeek(navController, currentGame)
                            }
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

@Composable
private fun RenderH2HWeek(
    navController: NavHostController,
    currentGame: ScheduleInfo,
    team: GetTeamInfoResponse
) {

    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.panel_bg))
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "${currentGame.gameType}, Week  ${currentGame.week}",
            color = colorResource(R.color.panel_fg),
            fontSize = 18.sp
        )
        if (currentGame.subType != "NA") {
            Text(
                textAlign = TextAlign.Center,
                text = currentGame.subType,
                color = colorResource(R.color.panel_fg),
                fontSize = 15.sp
            )
        }
        if (currentGame.played) {
            if (currentGame.result?.win == 1) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Win",
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            } else {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Loss",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Text(
                textAlign = TextAlign.Center,
                text = "${team.teamInfo.team_city} ${team.teamInfo.team_name} (${team.teamInfo.team_abbrev}) - ${currentGame.result?.score} ",
                color = colorResource(R.color.panel_fg),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                textAlign = TextAlign.Center,
                text = "${currentGame.opponent?.team_city} ${currentGame.opponent?.team_name} (${currentGame.opponent?.team_abbrev}) - ${currentGame.result?.opp_score} ",
                color = colorResource(R.color.panel_fg),
                fontSize = 14.sp
            )
            Text(" ", fontSize = 4.sp)
            Button(contentPadding = PaddingValues(all = 2.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                    contentColor = Color.White),
                modifier = Modifier.size(width = 360.dp, height = 34.dp),
                onClick = {
                    navController.navigate(
                        TeamRoutes.SCORECARD.replace(TeamRoutes.ARG_TAG_LEAGUE_ID, team.teamInfo.league_id.toString())
                            .replace(TeamRoutes.ARG_TAG_WEEK_NUM, currentGame.week.toString())
                            .replace(TeamRoutes.ARG_TAG_MATCHUP_NUM, currentGame.scorecardTeamNum.toString())
                    )
                }
            ) {
                Text("View Scorecard", fontSize = 16.sp)
            }
            Text(" ", fontSize = 4.sp)
        } else {
            if (currentGame.bye == "bye") {
                Text("Playoff Bye Week", fontSize = 18.sp, color = colorResource(R.color.panel_fg))
            } else {
                Text("Scheduled Game", fontSize = 18.sp, color = colorResource(R.color.panel_fg))
                Text("vs ${currentGame.opponent?.team_city} ${currentGame.opponent?.team_name} (${currentGame.opponent?.team_abbrev})",
                    fontSize = 15.sp, color = colorResource(R.color.panel_fg))
            }
        }

    }


}

@Composable
private fun RenderSurvivorWeek(navController: NavHostController, currentGame: ScheduleInfo) {
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.panel_bg))
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "${currentGame.gameType}, Week  ${currentGame.week}",
            color = colorResource(R.color.panel_fg),
            fontSize = 18.sp
        )
        if (currentGame.played) {
            Text(
                textAlign = TextAlign.Center,
                text = "Points: ${currentGame.result?.score}",
                color = colorResource(R.color.panel_fg),
                fontSize = 16.sp
            )
        } else {
            Text(
                textAlign = TextAlign.Center,
                text = "(upcoming week)",
                color = colorResource(R.color.panel_fg),
                fontSize = 16.sp
            )
        }
    }
}
