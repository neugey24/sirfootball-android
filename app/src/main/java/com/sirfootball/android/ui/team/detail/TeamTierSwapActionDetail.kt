package com.sirfootball.android.ui.team.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.LoadTeamRosterResponse
import com.sirfootball.android.data.model.LoadTeamTierRankingsResponse
import com.sirfootball.android.data.model.RosterSlotInfo
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.viewmodel.DataPersistenceViewModel

@Composable
fun TeamTierSwapActionDetail(teamId: Int, forSlot : RosterSlotInfo,
                           sheetValueChange: (Boolean) -> Unit,
                           updateNeeded: (Boolean) -> Unit,
                             tierInfo: LoadTeamTierRankingsResponse?,
                             rosterInfo: LoadTeamRosterResponse?
                           ) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =
    Modifier
        .padding(horizontal = 6.dp)
        .height(640.dp)
        .fillMaxWidth()

        ) {
        val tierName = tierInfo?.tierData?.get(forSlot.slotName) ?: "BAD"

        Text("Swap:", fontSize = 22.sp, textAlign = TextAlign.Center)
        Column(modifier = Modifier.background(
            color = SirGame.getTierColor(tierIn = tierName)
        )) {
            Row(           horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 4.dp,
                        vertical = 2.dp,
                    )) {
                Text("$tierName:  ")
                RenderTierInfo(tierName = tierName)
            }
            Row(           horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 4.dp,
                        vertical = 2.dp,
                    )) {
                Text(
                    textAlign = TextAlign.Center,
                    text = forSlot.slotName,
                    color = Color.White,
                    modifier = Modifier
                        .width(45.dp)
                        .background(
                            color = SirRoster.getSlotColor(slotIn = forSlot.slotName)
                        ),
                    fontSize = 14.sp
                )
                if (forSlot.slotPlayer != null) {
                    if (forSlot.slotPlayer.position == "DFST") {
                        Text(
                            textAlign = TextAlign.Left,
                            text = forSlot.slotPlayer.fullName,
                            color = colorResource(R.color.panel_fg),
                            modifier = Modifier.width(180.dp),
                            fontSize = 12.sp
                        )
                    } else {
                        Text(
                            textAlign = TextAlign.Left,
                            text = "   ${forSlot.slotPlayer.fullName} ${forSlot.slotPlayer.teamAbbrev}",
                            color = colorResource(R.color.panel_fg),
                            modifier = Modifier.width(180.dp),
                            fontSize = 12.sp
                        )
                    }

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

        }
        Text(" ", fontSize = 7.sp )
        Text("With:", fontSize = 22.sp, textAlign = TextAlign.Center)
        for (currentSlot in SirRoster.getStartingSlots()) {
            Text(" ", fontSize = 5.sp )
            val tierNameCurrent = tierInfo?.tierData?.get(currentSlot) ?: "BAD"
            val slotData = rosterInfo?.info?.slots?.get(currentSlot)
            if (currentSlot != forSlot.slotName && tierNameCurrent != tierName) {
                RenderTierSwapOccupant(
                    teamId = teamId,
                    slotName = forSlot.slotName,
                    slotData = slotData,
                    tierName = tierNameCurrent,
                  sheetValueChange, updateNeeded
                )
                
            }

        }

        Spacer(Modifier.weight(2f))
        Button(
            contentPadding = PaddingValues(all = 1.dp),
            modifier = Modifier
                .height(24.dp)
                .width(180.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            onClick =  {  sheetValueChange(false) },
        ) {
            Text("Cancel Tier Swap", fontSize = 14.sp )
        }
        Spacer(Modifier.weight(1f))

    }

}


@Composable
private fun RenderTierSwapOccupant(teamId: Int, slotName : String, slotData : RosterSlotInfo?, tierName: String,
                                   sheetValueChange: (Boolean) -> Unit,
                                   updateNeededIn: (Boolean) -> Unit
) {
    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value
    val actionExecuted = remember { mutableStateOf(false) }
    Column(modifier = Modifier.background(
        color = SirGame.getTierColor(tierIn = tierName)
    )) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 4.dp,
                    vertical = 2.dp,
                )
        ) {
            Text("$tierName:  ")
            RenderTierInfo(tierName = tierName)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 4.dp,
                    vertical = 2.dp,
                )
        ) {
            Spacer(Modifier.weight(1f))
            if (slotData != null) {
                Text(
                    textAlign = TextAlign.Center,
                    text = slotData.slotName,
                    color = Color.White,
                    modifier = Modifier
                        .width(45.dp)
                        .background(
                            color = SirRoster.getSlotColor(slotIn = slotData.slotName)
                        ),
                    fontSize = 14.sp
                )
            }

            if (slotData?.slotPlayer != null) {
                if (slotData.slotPlayer.position == "DFST") {
                    Text(
                        textAlign = TextAlign.Left,
                        text = "   ${slotData.slotPlayer.fullName}",
                        color = colorResource(R.color.panel_fg),
                        modifier = Modifier.width(180.dp),
                        fontSize = 12.sp
                    )
                } else {
                    Text(
                        textAlign = TextAlign.Left,
                        text = "   ${slotData.slotPlayer.fullName} ${slotData.slotPlayer.teamAbbrev}",
                        color = colorResource(R.color.panel_fg),
                        modifier = Modifier.width(180.dp),
                        fontSize = 12.sp
                    )
                }

            } else {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Empty Slot",
                    color = colorResource(R.color.panel_fg),
                    modifier = Modifier.width(260.dp),
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.weight(2f))
            Button(
                contentPadding = PaddingValues(all = 1.dp),
                modifier = Modifier
                    .height(24.dp)
                    .width(82.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                onClick = {
                    if (slotData != null) {
                        // perform swap action
                        actionExecuted.value = true
                        saveViewModel.performTierRankingSwap(teamId, slotName, slotData.slotName)
                    }
                },
            ) {
                Text("Swap", fontSize = 14.sp)
            }
            Spacer(Modifier.weight(1f))
        }
    }

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block")
            if (actionExecuted.value) {
                Log.i("Save", "Tier Swap successful")
                updateNeededIn(true)
                sheetValueChange(false)
                actionExecuted.value = false
            }

        }

        else -> {
            Log.i("Save", "Tier swap failed")
        }
    }

}

@Composable
private fun RenderTierInfo(tierName: String) {
    when (tierName) {
        "S" -> Text("   +100% points", fontSize = 18.sp, color = Color.Black)
        "A" -> Text("   +50% points", fontSize = 18.sp, color = Color.Black)
        "B" -> Text("   normal points", fontSize = 18.sp, color = Color.Black)
        "C" -> Text("   -25% points", fontSize = 18.sp, color = Color.Black)
        else -> {
            Text(" -75% points", fontSize = 18.sp, color = Color.Black)
        }
    }

}




