package com.sirfootball.android.ui.team.detail

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.LoadTeamRosterResponse
import com.sirfootball.android.data.model.LoadTeamTierRankingsResponse
import com.sirfootball.android.data.model.RosterSlotInfo
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.viewmodel.TeamTierRankingsViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TeamTierRankingsPage(teamId : Int) {
    val viewModel = hiltViewModel<TeamTierRankingsViewModel>()
    val loadState = viewModel.getTierRankingsResponse.value

    val sheetUp = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var updateNeeded by remember { mutableStateOf(false) }
    var showSheetForSlot by remember { mutableStateOf(RosterSlotInfo()) }

    var tierInfo : LoadTeamTierRankingsResponse? = null
    var roster : LoadTeamRosterResponse? = null

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId)
    }

    when(updateNeeded) {
        true -> { updateNeeded = false
            viewModel.fetch(teamId)
            Log.i("Roster", "show me sheet state: $showBottomSheet")
        }
        false -> Log.i("Roster", "Update not needed")
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
                tierInfo = responseData.tier
                roster = responseData.roster

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
                Text("Tier Rankings", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Update Rankings - NFL Week ${tierInfo?.weekNum}", fontSize = 22.sp)
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

                        for (currTier in SirGame.ALL_TIERS) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = SirGame.getTierColor(tierIn = currTier)
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
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
                                        Text(text = currTier, fontSize = 28.sp, color = Color.Black)
                                        RenderTierInfo(currTier)
                                    }

                                    for (currentSlot in SirRoster.getStartingSlots()) {

                                        if (tierInfo?.tierData?.get(currentSlot).equals(currTier)) {
                                            RenderTierOccupant(currentSlot, roster!!,
                                                sheetValueChange = { showBottomSheet = it },
                                                sheetValueSlot = { showSheetForSlot = it}
                                                )
                                        }


                                    }

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

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = colorResource(R.color.panel_bg),
            contentColor = colorResource(R.color.panel_fg),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetUp
        ) {
            TeamTierSwapActionDetail(teamId = teamId, forSlot = showSheetForSlot, tierInfo = tierInfo,
                rosterInfo = roster,
                sheetValueChange = {showBottomSheet = it}, updateNeeded = {updateNeeded = it})
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

@Composable
private fun RenderTierOccupant( slotName : String, roster : LoadTeamRosterResponse,
                               sheetValueChange: (Boolean) -> Unit ,
                               sheetValueSlot: (RosterSlotInfo) -> Unit
) {
    val slotData = roster.info.slots[slotName]

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
        Text(
            textAlign = TextAlign.Center,
            text = slotName,
            color = Color.White,
            modifier = Modifier
                .width(45.dp)
                .background(
                    color = SirRoster.getSlotColor(slotIn = slotName)
                ),
            fontSize = 14.sp
        )

        if (slotData?.slotPlayer != null) {
            if (slotData.slotPlayer.position == "DFST") {
                Text(
                    textAlign = TextAlign.Left,
                    text = slotData.slotPlayer.fullName,
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
            modifier = Modifier.height(24.dp).width(82.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            onClick =  {
                if (slotData != null) {
                    sheetValueSlot(slotData)
                    sheetValueChange(true)
                }
            },
        ) {
            Text("Swap", fontSize = 14.sp )
        }
        Spacer(Modifier.weight(1f))
    }

}




