package com.sirfootball.android.ui.team.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.GamePickData
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.ScheduledGameData
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.TeamPickEmListViewModel

@Composable
fun TeamPickEmPage(teamId : Int) {
    val viewModel = hiltViewModel<TeamPickEmListViewModel>()
    val loadState = viewModel.getPicksListResponse.value

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId)
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
                val team = responseData.info
                val picksInfo = responseData.picks

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
                Text("Decisions Decisions", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("NFL Weekly Pick'Em - Week ${picksInfo.weekNum}", fontSize = 22.sp)
                Spacer(Modifier.weight(1f))

                LazyColumn(modifier = Modifier.fillMaxWidth().background(color =
                colorResource(R.color.panel_bg)).size(410.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                    for (entry in picksInfo.games) {
                        val gameData = picksInfo.games[entry.key]
                        if (gameData?.home_team_abbrev == entry.key) {
                            item {
                                RenderPickEmRow(entry.key, entry.value, picksInfo.weekCommenced,
                                    picksInfo.picks, saveViewModel, teamId)
                            }
                        }
                    }

                }
                Spacer(Modifier.weight(2f))
            }


            else -> {
                val error = loadState.toString()
                Text("Error loading team Occurred: $error")
            }
        }
    }

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state - save pick em")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - save pick em")
            viewModel.fetch(teamId)
        }

        else -> {
            Log.i("Save", "Roster update failed - save pick em")
        }
    }

}

@Composable
fun RenderPickEmRow(
    homeTeamAbbrev: String,
    gameData: ScheduledGameData,
    weekCommenced: Boolean,
    picks: Map<String, GamePickData>,
    saveViewModel: DataPersistenceViewModel,
    teamId: Int
) {

    val pickData = picks[homeTeamAbbrev]

    Row {
        Spacer(Modifier.weight(1f))
        Text("${gameData.happyTime} CST", fontWeight = FontWeight.Bold,
            color = colorResource(R.color.panel_fg), fontSize = 13.sp, modifier = Modifier.width(150.dp))
        Spacer(Modifier.weight(1f))
        Text(gameData.away_team_abbrev, color = colorResource(R.color.panel_fg), fontSize = 15.sp,
            modifier = Modifier.width(40.dp), textAlign = TextAlign.Right)
        if (pickData?.selection == gameData.away_team_abbrev) {
            Icon(
                painter = painterResource(id = R.drawable.ic_circle_star_black_24dp),
                contentDescription = "Selected Team",
                tint = Color.Blue,
                modifier = Modifier.size(24.dp).clickable( onClick = {
                })
            )
        } else {
            if (weekCommenced) {
                Text("-", color = Color.Gray, fontSize = 15.sp, modifier = Modifier.width(24.dp))
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle_blank_black_24dp),
                    contentDescription = "Unselected Team",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp).clickable( onClick = {
                        saveViewModel.savePick(teamId = teamId,
                            homeTeamAbbrev = gameData.home_team_abbrev, homeWin = "N")
                    })
                )
            }
        }
        Text(" AT ", color = colorResource(R.color.panel_fg), textAlign = TextAlign.Center,
            fontSize = 13.sp, modifier = Modifier.width(32.dp))
        if (pickData?.selection == gameData.home_team_abbrev) {
            Icon(
                painter = painterResource(id = R.drawable.ic_circle_star_black_24dp),
                contentDescription = "Selected Team",
                tint = Color.Blue,
                modifier = Modifier.size(24.dp).clickable( onClick = {
                })
            )
        } else {
            if (weekCommenced) {
                Text("-", color = Color.Gray, fontSize = 15.sp, modifier = Modifier.width(24.dp))
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle_blank_black_24dp),
                    contentDescription = "Unselected Team",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp).clickable( onClick = {
                        saveViewModel.savePick(teamId = teamId,
                            homeTeamAbbrev = gameData.home_team_abbrev, homeWin = "Y")
                    })
                )
            }
        }
        Text(gameData.home_team_abbrev, color = colorResource(R.color.panel_fg), fontSize = 15.sp, modifier = Modifier.width(40.dp))
        Spacer(Modifier.weight(1f))
    }




}






