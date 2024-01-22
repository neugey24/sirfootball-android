package com.sirfootball.android.ui.team

import android.util.Log
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.PlayerForAddData
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.GetTeamAddPlayerViewModel

@Composable
fun TeamRosterAddPage(navController: NavHostController, teamId : Int,
                      slotName: String, isDr: String, requestPos: String
                      ) {
    val viewModel = hiltViewModel<GetTeamAddPlayerViewModel>()
    val loadState = viewModel.getTeamAddCompositeResponse.value

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId, slotName, isDr, requestPos)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text("Loading Data ...")
        }

        is ApiState.Success -> {
            val responseData = loadState.data
            val addData = responseData.add
            val team = responseData.info

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
                    modifier = Modifier.size(116.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = team.teamInfo.team_city, fontSize = 24.sp)
                    Text(text = team.teamInfo.team_name, fontSize = 24.sp)
                    Text(text = "(${team.teamInfo.team_abbrev})", fontSize = 22.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.padding(top = 5.dp, start = 25.dp, end = 25.dp)) {
                    Spacer(Modifier.weight(1f))
                    if (addData.data.posAvailable.size > 1) {
                        for (position in addData.data.posAvailable) {
                            if (position != addData.data.posView) {
                                    Button(
                                        contentPadding = PaddingValues(all = 1.dp),
                                        modifier = Modifier.height(36.dp).width(55.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                                            contentColor = Color.White),
                                        onClick = {
                                            navController.navigate(
                                                TeamRoutes.TEAM_ROSTER_ADD.replace(
                                                    TeamRoutes.ARG_TAG_TEAM_ID, teamId.toString())
                                                    .replace(TeamRoutes.ARG_TAG_SLOT_NAME, slotName)
                                                    .replace(TeamRoutes.ARG_TAG_IS_DR, "N")
                                                    .replace(TeamRoutes.ARG_TAG_REQUEST_POS, position)
                                            )

                                        },
                                    ) {
                                        Text(position, fontSize = 16.sp )
                                    }
                            } else {
                                Text(
                                    text = "$position  ", fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                        .clickable(onClick = {})
                                )
                            }
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
                    Text(
                        text = "Add Free Agent: ${addData.data.posView}", fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    if (addData.data.slotName != "UNK") {
                        Text(
                            text = "For Slot: ${addData.data.slotName}", fontSize = 15.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    if (isDr == "Y") {
                        Log.i("Roster", "Add DR code!")
                    }


                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(408.dp)
                ) {
                    for (freeAgent in addData.data.players) {
                        item {
                            DisplayFreeAgent(teamId, navController, freeAgent, slotName,
                                saveViewModel, saveState)
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
private fun DisplayFreeAgent(teamId: Int, navController: NavHostController, player: PlayerForAddData, slotName: String,
                             saveViewModel: DataPersistenceViewModel, saveState: ApiState<GeneralPersistenceResponse>) {
    val notOnWaivers = player.clear_day == null && player.association_lock == 0
    val statusMessage = if (notOnWaivers) "(Free Agent)" else
            (if (player.clear_day != null) "(Waivers -" + player.clear_day + ")" else "(Waivers - TUE)")
    // First Row - Slot/Name/Caliber/Points
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(color = colorResource(R.color.panel_bg))
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            )
    ) {

        if (player.fantasy_position == "DFST") {
            Text(
                textAlign = TextAlign.Left,
                text = player.full_name,
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(160.dp),
                fontSize = 12.sp
            )
        } else {
            Text(
                textAlign = TextAlign.Left,
                text = "${player.fantasy_position} ${player.full_name} ${player.team_abbreviation}",
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(160.dp),
                fontSize = 12.sp
            )
        }
        Spacer(Modifier.weight(1f))
        // Bye
        Text(
            textAlign = TextAlign.Center,
            text = "Bye: ${player.bye_week}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(50.dp).background(Color.Cyan),
            fontSize = 13.sp
        )
        Spacer(Modifier.weight(1f))
        // Caliber
        Text(
            textAlign = TextAlign.Right,
            text = "●●●●●".substring(0, (player.caliber)),
            color = colorResource(R.color.panel_fg),
            modifier = Modifier.width(42.dp),
            fontSize = 13.sp
        )
        Text(
            textAlign = TextAlign.Right,
            text = "  ",
            color = colorResource(R.color.panel_fg),
            modifier = Modifier.width(10.dp),
            fontSize = 14.sp
        )
        // Tot pts
        Text(
            textAlign = TextAlign.Right,
            text = player.fpoints ?: "0",
            color = colorResource(R.color.panel_fg),
            modifier = Modifier.width(50.dp),
            fontSize = 12.sp
        )

    }

    // Second Row - Season Stat Line
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                text = player.stat_brief,
                color = colorResource(R.color.panel_fg),
                fontSize = 12.sp
            )
    }

    // Third Row - Info/injury, Status and Add button
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(color = colorResource(R.color.panel_action_bg))
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            )
    ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_info_black_24dp),
                contentDescription = "Player Info",
                tint = Color.Blue,
                modifier = Modifier.size(20.dp).clickable(onClick = {
                    navController.navigate(
                        TeamRoutes.PLAYER_INFO.replace(TeamRoutes.ARG_TAG_PLAYER_ID, player.nfl_player_id.toString()))
                })
            )
            if (player.injury_status != "") {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hospital_black_24dp),
                    contentDescription = "Player Info - Injury",
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp).clickable(onClick = {
                        navController.navigate(
                            TeamRoutes.PLAYER_INFO.replace(TeamRoutes.ARG_TAG_PLAYER_ID, player.nfl_player_id.toString()))
                    })
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                textAlign = TextAlign.Center,
                text = statusMessage,
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(180.dp),
                fontSize = 13.sp
            )
            Spacer(Modifier.weight(1f))
            Button(
                contentPadding = PaddingValues(all = 1.dp),
                modifier = Modifier.height(24.dp).width(82.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                    contentColor = Color.White),
                onClick =  {
                    if (slotName == "UNK") {
                        // Proceed to slot selection page
                        navController.navigate(
                            TeamRoutes.TEAM_ROSTER_ADD_SLOT.replace(
                                TeamRoutes.ARG_TAG_TEAM_ID, teamId.toString())
                                .replace(TeamRoutes.ARG_TAG_PLAYER_ID, player.nfl_player_id.toString()
                                .replace(TeamRoutes.ARG_TAG_ON_WAIVERS, if (notOnWaivers) "N" else "Y"))
                        )
                    } else {
                        // Perform Add Action
                        saveViewModel.teamAddPerformExecute(teamId = teamId, playerId = player.nfl_player_id,
                            slotName = slotName, onWaivers = if (notOnWaivers) "N" else "Y"
                            )
                    }
                },
            ) {
                Text("Add Player", fontSize = 14.sp )
            }


    }

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state - roster add")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - roster add")
            navController.navigate(
                TeamRoutes.TEAM_ROSTER.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamId.toString()))
        }

        else -> {
            Log.i("Save", "Roster update failed - roster add")
        }
    }
}
