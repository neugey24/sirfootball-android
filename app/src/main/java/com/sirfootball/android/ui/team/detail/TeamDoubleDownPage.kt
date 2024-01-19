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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.SlotPlayer
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.TeamDoubleDownListViewModel

@Composable
fun TeamDoubleDownPage(teamId : Int) {
    val viewModel = hiltViewModel<TeamDoubleDownListViewModel>()
    val loadState = viewModel.getDoubleDownListResponse.value

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
                val ddInfo = responseData.dd
                val roster = responseData.roster

                val currSelPlayer = if (ddInfo.dd[ddInfo.weekNum.toString()] != null) ddInfo.dd[ddInfo.weekNum.toString()] else -1
                val lockedPlayer1 = if (ddInfo.weekNum > 1 && ddInfo.dd[(ddInfo.weekNum - 1).toString()] != null)
                     ddInfo.dd[(ddInfo.weekNum - 1).toString()] else -1
                val lockedPlayer2 = if (ddInfo.weekNum > 2 && ddInfo.dd[(ddInfo.weekNum - 2).toString()] != null)
                     ddInfo.dd[(ddInfo.weekNum - 2).toString()] else -1

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
                Text("Double Down", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Player Selection - NFL Week ${ddInfo.weekNum}", fontSize = 22.sp)
                Spacer(Modifier.weight(1f))

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color =
                        colorResource(R.color.panel_bg)
                    )
                    .size(410.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                    for (entry in SirRoster.getStartingSlots()) {
                        if (roster.info.slots[entry]?.slotPlayer != null) {
                            item {
                                RenderDoubleDown( entry, roster.info.slots[entry]?.slotPlayer ?: SlotPlayer(), ddInfo.weekCommenced,
                                    saveViewModel, teamId,  currSelPlayer ?: -1, lockedPlayer1 ?: -1,
                                    lockedPlayer2 ?: -1, ddInfo.weekNum )
                                Divider()
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
            Log.i("Save", "Loading the save state - save double down selection")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - save double down selection")
            viewModel.fetch(teamId)
        }

        else -> {
            Log.i("Save", "Roster update failed - save double down selection")
        }
    }

}

@Composable
fun RenderDoubleDown(
    slotName: String,
    slotPlayer: SlotPlayer,
    weekCommenced: Boolean,
    saveViewModel: DataPersistenceViewModel,
    teamId: Int,
    currSelPlayer: Int,
    lockedPlayer1: Int,
    lockedPlayer2: Int,
    weekNum: Int
) {

    Row {
        Spacer(Modifier.weight(1f))

        if (slotPlayer.playerId == lockedPlayer1 || slotPlayer.playerId == lockedPlayer2) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock_black_24dp),
                contentDescription = "Locked Player",
                tint = Color.Blue,
                modifier = Modifier
                    .size(24.dp)
            )
        } else {
            if (slotPlayer.playerId == currSelPlayer) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle_star_black_24dp),
                    contentDescription = "Selected Player",
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(24.dp)
                )
            } else {
                if (weekCommenced) {
                    Text("-", color = Color.Gray)
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle_blank_black_24dp),
                        contentDescription = "Unselected Spell",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = {
                                saveViewModel.saveDoubleDown(
                                    teamId = teamId, playerId = slotPlayer.playerId, weekNum = weekNum)
                            })
                    )
                }


            }
        }
        Spacer(Modifier.weight(1f))
            Text("${slotName}: ${slotPlayer.position} ${slotPlayer.fullName} ${slotPlayer.teamAbbrev} - Bye ${slotPlayer.byeWeek}",
                color = colorResource(R.color.panel_fg), fontSize = 13.sp, modifier = Modifier.width(300.dp))
        Spacer(Modifier.weight(1f))

    }

}






