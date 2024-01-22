package com.sirfootball.android.ui.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.UserTeam
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.ui.nav.AddRoutes
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.ui.nav.UserRoutes
import com.sirfootball.android.viewmodel.GetTeamsViewModel


@Composable
fun LockerRoomPage(navController: NavHostController) {
    val viewModel = hiltViewModel<GetTeamsViewModel>()
    val loadState = viewModel.getTeamsResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }
    val padTop = Modifier.padding(top = 25.dp)
    Row(verticalAlignment = Alignment.Top, modifier = padTop) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home_black_24dp),
            contentDescription = "Locker Room",
            modifier = Modifier.size(46.dp)
        )
        Text(text = " Your Teams", fontSize = 28.sp)
        Spacer(Modifier.weight(1f))
        Button(contentPadding = PaddingValues(all = 2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            modifier = Modifier.size(width = 140.dp, height = 34.dp),
            onClick = {
                navController.navigate( UserRoutes.CROWN_DISPLAY)
            }
        ) {
            Text("Crowns", fontSize = 14.sp)
        }
    }

    Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {
        when (loadState) {
            is ApiState.Loading -> {
                Text("Loading Data ...")
            }

            is ApiState.Success -> {
                val responseData = loadState.data
                if (responseData.leaguesAvailable) {
                    if (responseData.userTeams.isEmpty()) {
                        Text(text = "You have no teams yet.", fontSize = 26.sp)
                        Text("")
                        Text("Click on the Add (+) button to find a league.")
                    } else {
                       // Text("$responseData.userTeams.size found - TBD list teams")
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier
                                    .padding( start = 8.dp, end = 8.dp)
                                        .size(570.dp)

                        ) {
                            for (teamInfo in responseData.userTeams ) {
                                item {
                                    RenderTeamBlock(teamInfo, navController)
                                }
                             }
                        }
                    }
                } else {
                    Text(text = "Leagues not yet available", fontSize = 26.sp)
                    Text("")
                    Text("It's the offseason!  Please visit sirfootball.com on laptop/PC for game information.")
                }
            }

            else -> {
                val error = loadState.toString()
                Text("Error loading teamsOccurred: $error")
            }
        }

    } // end column

}

@Composable
fun RenderTeamBlock(teamInfo: UserTeam, navController: NavHostController) {

    Row(

        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.background(color = colorResource(R.color.panel_bg)).fillMaxWidth().padding(
            horizontal = 8.dp,
            vertical = 8.dp,
        ).clickable(enabled =  true, onClick = {
            navController.navigate(
                TeamRoutes.TEAM_HOME.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamInfo.team_id.toString()))
        })) {

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
            //  Text(logoPath, color = colorResource(R.color.panel_fg))
            Column {
                Text(
                    "${teamInfo.team_city} ${teamInfo.team_name} (${teamInfo.team_abbrev})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.panel_fg)
                )
                Text(
                    "League: ${teamInfo.league_name}",
                    fontSize = 15.sp,
                    color = colorResource(R.color.panel_fg)
                )
                Text(
                    "Scoring: ${teamInfo.scoring_type}",
                    fontSize = 15.sp,
                    color = colorResource(R.color.panel_fg)
                )
                Text(
                    "${SirGame.GAME_DATA[teamInfo.game_abbrev]?.get("name")}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.panel_fg)
                )
            }
        }


}