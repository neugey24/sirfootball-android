package com.sirfootball.android.ui.team

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.SlotForAdd
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.LoadAddSlotGatherViewModel

@Composable
fun TeamRosterAddSlotPage(navController: NavHostController, teamId : Int,
                      playerId: Int, onWaivers: String
                      ) {
    val viewModel = hiltViewModel<LoadAddSlotGatherViewModel>()
    val loadState = viewModel.getTeamAddCompositeResponse.value

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId, playerId)
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

                Text(
                    text = "Add Player: ${addData.data.playerRecord.fantasy_position} ${addData.data.playerRecord.full_name} ${addData.data.playerRecord.team_abbreviation}", fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(
                    text = "Select slot or player to drop:", fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(408.dp)
                ) {
                    for (slotForAdd in addData.data.slots) {
                        if (slotForAdd.value.eligible) {
                            item {
                                DisplayAvailableSlots(teamId, playerId, navController, slotForAdd.value, saveViewModel, saveState, onWaivers)
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

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state - roster add for slot")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - roster add for slot")
            navController.navigate(
                TeamRoutes.TEAM_ROSTER.replace(TeamRoutes.ARG_TAG_TEAM_ID, teamId.toString()))
        }

        else -> {
            Log.i("Save", "Roster update failed - roster add for slot")
        }
    }

}

@Composable
private fun DisplayAvailableSlots(
    teamId: Int, playerId: Int,
    navController: NavHostController,
    slotForAdd: SlotForAdd,
    saveViewModel: DataPersistenceViewModel,
    saveState: ApiState<GeneralPersistenceResponse>,
    onWaivers: String
) {

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

        Text(
            textAlign = TextAlign.Center,
            text = slotForAdd.slot_name,
            color = Color.White,
            modifier = Modifier
                .width(45.dp)
                .background(
                    color = SirRoster.getSlotColor(slotIn = slotForAdd.slot_name)
                ),
            fontSize = 14.sp
        )

        if (slotForAdd.player_id != null) {
            if (slotForAdd.player?.pos == "DFST") {
                Text(
                    textAlign = TextAlign.Left,
                    text = slotForAdd.player.fullName,
                    color = colorResource(R.color.panel_fg),
                    modifier = Modifier.width(180.dp),
                    fontSize = 12.sp
                )
            } else {
                Text(
                    textAlign = TextAlign.Left,
                    text = "${slotForAdd.player?.pos} ${slotForAdd.player?.fullName} ${slotForAdd.player?.team}",
                    color = colorResource(R.color.panel_fg),
                    modifier = Modifier.width(180.dp),
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.weight(1f))
            // Caliber 50
            Text(
                textAlign = TextAlign.Right,
                text = "●●●●●".substring(0, (slotForAdd.player?.caliber ?: 0)),
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(50.dp),
                fontSize = 14.sp
            )
            // Tot pts 30
            Text(
                textAlign = TextAlign.Right,
                text = slotForAdd.player?.fpoints ?: "0",
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(30.dp),
                fontSize = 14.sp
            )

        } else {
            Text(
                textAlign = TextAlign.Center,
                text = "Empty Slot",
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(260.dp),
                fontSize = 12.sp
            )
        }
    }

    // Second Row - Bye
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
        if (slotForAdd.player_id != null) {
            Spacer(Modifier.weight(1f))
            Text(
                textAlign = TextAlign.Center,
                text = " Bye: ${slotForAdd.player?.bye} ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(100.dp).background(Color.Cyan),
                fontSize = 13.sp
            )
            Spacer(Modifier.weight(1f))
        } else {
            Text(" ", fontSize = 13.sp)
        }

    }

    // Third Row - Drop Player button
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
        Spacer(Modifier.weight(1f))
        Button(
            contentPadding = PaddingValues(all = 1.dp),
            modifier = Modifier.height(24.dp).width(172.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            onClick = {
                saveViewModel.teamAddPerformExecute(teamId = teamId, playerId, slotForAdd.slot_name, onWaivers = onWaivers )
            },
        ) {
            Text("Drop Player/Choose Slot", fontSize = 14.sp )
        }
        Spacer(Modifier.weight(1f))

    }
}
