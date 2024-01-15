package com.sirfootball.android.ui.team

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
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
import com.sirfootball.android.data.model.RosterSlotInfo
import com.sirfootball.android.data.model.TeamRosterInfo
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.GetTeamInfoViewModel
import com.sirfootball.android.viewmodel.GetTeamRosterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRosterPage(navController: NavHostController, teamId : Int) {
    val viewModel = hiltViewModel<GetTeamRosterViewModel>()
    val loadState = viewModel.getTeamRosterResponse.value

    val sheetUp = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

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
            val roster = responseData.roster

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
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.padding(top = 5.dp, start = 25.dp, end = 25.dp)) {
                    Text(
                        text = "Team Roster:", fontSize = 20.sp, fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    ElevatedButton(contentPadding = PaddingValues(all = 8.dp),
                        onClick = {
                        }
                    ) {
                        Text(" Add Player ", fontSize = 18.sp)
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(408.dp)
                ) {
                    item {
                        Text("NFL Week ${team.weekNum} Starters", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    }
                    for (currentSlot in SirRoster.getStartingSlots()) {
                        item {
                            RenderTeamRosterSlot(
                                navController,
                                currentSlot, roster.info,
                                sheetValueChange = { showBottomSheet = it },
                            )
                        }
                    }
                    item {
                        Text("NFL Week ${team.weekNum} Bench", fontSize = 20.sp)
                    }
                    for (currentSlot in SirRoster.getBenchSlots()) {
                        item {
                            RenderTeamRosterSlot(
                                navController,
                                currentSlot,
                                roster.info,
                                sheetValueChange = { showBottomSheet = it },
                            )
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
private fun RenderTeamRosterSlot(navController: NavHostController, currentSlot: String, info: TeamRosterInfo,
                                 sheetValueChange: (Boolean) -> Unit                           ) {
    val slotData = info.slots[currentSlot]
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
            text = currentSlot,
            color = Color.White,
            modifier = Modifier
                .width(45.dp)
                .background(
                    color = SirRoster.getSlotColor(slotIn = currentSlot)
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
                    text = "${slotData.slotPlayer.fullName} ${slotData.slotPlayer.teamAbbrev} #${slotData.slotPlayer.jerseyNum}",
                    color = colorResource(R.color.panel_fg),
                    modifier = Modifier.width(180.dp),
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.weight(1f))
            // Caliber 50
            Text(
                textAlign = TextAlign.Right,
                text = "●●●●●".substring(0, (slotData.slotPlayer.caliber)),
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(50.dp),
                fontSize = 14.sp
            )
            // Tot pts 30
            Text(
                textAlign = TextAlign.Right,
                text = slotData.slotPlayer.fantasyPoints ?: "0",
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

    // Second Row - Bye and current sched game/result
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
        if (slotData?.slotPlayer == null) {
            ElevatedButton(contentPadding = PaddingValues(all = 8.dp),
                onClick = {
                }
            ) {
                Text(" Add Player ", fontSize = 15.sp)
            }
        } else {
            Text(
                textAlign = TextAlign.Center,
                text = " Bye: ${slotData.slotPlayer.byeWeek} ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(100.dp).background(Color.Cyan),
                fontSize = 13.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                textAlign = TextAlign.Center,
                text = renderStatBriefOrGame(info, slotData),
                color = colorResource(R.color.panel_fg),
                modifier = Modifier.width(180.dp),
                fontSize = 13.sp
            )
        }


    }

    // Third Row - Info/injury and action button(s)
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
        if (slotData?.slotPlayer == null) {
            Text(" ", fontSize = 16.sp)
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_info_black_24dp),
                contentDescription = "Player Info",
                tint = Color.Blue,
                modifier = Modifier.size(20.dp).clickable(onClick = {
                    navController.navigate(
                        TeamRoutes.PLAYER_INFO.replace(TeamRoutes.ARG_TAG_PLAYER_ID, slotData.slotPlayer.playerId.toString()))
                })
            )
            if (slotData.slotPlayer.hasInjury) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hospital_black_24dp),
                    contentDescription = "Player Info - Injury",
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp).clickable(onClick = {

                    })
                )
            }
            Spacer(Modifier.weight(1f))
            if (slotData.isLocked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock_black_24dp),
                    contentDescription = "Locked",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                 Text(" <<Actions>> ", fontSize = 16.sp, color = Color.Blue, fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(onClick = {}))

                if (info.waiverTimeframe == "game" && (!(currentSlot.startsWith("B"))) && slotData.lockPlayerInfo == null) {
                    Text(" <<DR>> ", fontSize = 16.sp, color = Color.Blue, fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = {}))
                }
            }

        }


    }


}

@Composable
private fun renderStatBriefOrGame(info: TeamRosterInfo, slotData: RosterSlotInfo): String {
    var result = ""
    if (info.weekNumber == 0) {
        result = "Preseason"
    } else {
        if (slotData.slotPlayer?.teamAbbrev == null) {
            result = "Free Agent"
        } else {
            if (slotData.slotPlayer.byeWeek == info.weekNumber) {
                result = "Player Bye Week"
            } else {
                val gameInfo = slotData.slotPlayer.gameInfo
                if (gameInfo == null || gameInfo.gameStatus == "Scheduled") {
                    val matchingGame = info.weeklySchedule[slotData.slotPlayer.teamAbbrev]
                    val vsAt = if (slotData.slotPlayer.teamAbbrev == matchingGame?.home_team_abbrev)
                     "vs " + matchingGame.away_team_abbrev else "at " + (matchingGame?.home_team_abbrev)
                    result = "${vsAt} ${matchingGame?.happyTime} CST"
                } else {
                    result = gameInfo.statBrief
                }
            }
        }
    }
    return result
}