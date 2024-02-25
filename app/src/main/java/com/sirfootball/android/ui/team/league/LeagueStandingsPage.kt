package com.sirfootball.android.ui.team.league

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.TeamInStandings
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.viewmodel.GetLeagueStandingsViewModel


@Composable
fun LeagueStandingsPage(leagueId : Int) {
    val viewModel = hiltViewModel<GetLeagueStandingsViewModel>()
    val loadState = viewModel.getLeagueStandingsResponse.value

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
            val info = responseData.standings

            val gameLogoId = LocalContext.current.resources.getIdentifier(
                "game_logo_${SirGame.GAME_DATA[info.league.game_abbrev]?.get("logo")}", "drawable",
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
                    Text(text = "${SirGame.GAME_DATA[info.league.game_abbrev]?.get("name")}", fontSize = 24.sp)
                    Text(text = info.league.league_name, fontSize = 16.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Text("League Standings", fontSize = 28.sp)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(458.dp)
                ) {
                    for (currentDivision in info.divisions) {
                        item {
                            teamCount = 0
                            Text("${currentDivision.name} Division", fontSize = 25.sp)
                        }
                        for (currentTeam in currentDivision.teams) {
                            item {
                                teamCount += 1
                                RenderTeamStandingsBlock(currentTeam, teamCount )
                            }
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
fun RenderTeamStandingsBlock(currentTeam: TeamInStandings, teamCountIn: Int ) {
    Row(

        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.background(color = colorResource(R.color.panel_bg)).fillMaxWidth().padding(
            horizontal = 8.dp,
            vertical = 8.dp,
        )) {
        Column {
//            val placeName =
//                when(teamCountIn) {
//                    1 -> "1st"
//                    2 -> "2nd"
//                    3 -> "3rd"
//                    else -> teamCountIn.toString() + "th"
//                }
//            Text(placeName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = colorResource(R.color.panel_fg))
            Text("Win/Loss:", fontSize = 18.sp, color = colorResource(R.color.panel_fg))
            Text("${currentTeam.curr_wins}-${currentTeam.curr_losses}", fontSize = 18.sp,
                color = colorResource(R.color.panel_fg))
            Text("Point Diff:", fontSize = 18.sp, color = colorResource(R.color.panel_fg))
            Text(currentTeam.pt_diff, fontSize = 18.sp,
                color = colorResource(R.color.panel_fg))
        }
        Column {
            Image(
                painter = painterResource(
                    id = SirAvatar.getResourceIdForAvatar(
                        currentTeam.avatar_key,
                        currentTeam.team_num
                    )
                ),
                contentDescription = "Team Avatar",
                modifier = Modifier.size(120.dp)
            )
            Text(currentTeam.team_city, fontSize = 18.sp,
                color = colorResource(R.color.panel_fg))
            Text(currentTeam.team_name, fontSize = 18.sp, color = colorResource(R.color.panel_fg))
            Text("(${currentTeam.team_abbrev})", fontSize = 18.sp,
                color = colorResource(R.color.panel_fg))

        }

    }

}
