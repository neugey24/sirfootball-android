package com.sirfootball.android.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.PreviewTeamInfo
import com.sirfootball.android.data.model.PreviewTeamSlot
import com.sirfootball.android.data.model.ScheduledGameData
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.viewmodel.GetPreviewViewModel


@Composable
fun PreviewPage(leagueId : Int, weekNum: Int, matchupNum: Int) {
    val viewModel = hiltViewModel<GetPreviewViewModel>()
    val loadState = viewModel.getPreviewResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(leagueId, weekNum, matchupNum)
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
                val gameLogoId = LocalContext.current.resources.getIdentifier(
                    "game_logo_${SirGame.GAME_DATA[responseData.preview.teamInfo.game_abbrev]?.get("logo")}",
                    "drawable",
                    LocalContext.current.packageName
                )

                val padTop = Modifier.padding(top = 18.dp)
                // Heading
                Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                    Image(
                        painter = painterResource(id = gameLogoId),
                        contentDescription = "Game Type",
                        modifier = Modifier.size(100.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${
                                SirGame.GAME_DATA[responseData.preview.teamInfo.game_abbrev]?.get(
                                    "name"
                                )
                            }", fontSize = 24.sp
                        )
                        Text(
                            text = "${responseData.preview.teamInfo.league_name}",
                            fontSize = 16.sp
                        )
                        Text(
                            text = "${responseData.matchupTitle}, Week ${responseData.weekNum}",
                            fontSize = 14.sp
                        )
                    }

                }
                Text(text = " ", fontSize = 10.sp)
                // Matchup Info
                val matchupWidths = arrayOf(180.dp, 70.dp)
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.preview.teamInfo.team_city,
                            modifier =
                            Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.preview.oppInfo.team_city,
                            modifier = Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.preview.teamInfo.team_name,
                            modifier = Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.preview.oppInfo.team_name,
                            modifier = Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        Image(
                            painter = painterResource(
                                id = SirAvatar.getResourceIdForAvatar(
                                    responseData.preview.teamInfo.avatar_key,
                                    responseData.preview.teamInfo.team_num
                                )
                            ),
                            contentDescription = "Team Avatar",
                            modifier = Modifier
                                .size(140.dp)
                                .padding(start = 20.dp, end = 20.dp)
                        )
                        Text(
                            textAlign = TextAlign.Center, text = " VS ", fontSize = 40.sp,
                            modifier = Modifier
                                .width(matchupWidths[1])
                                .padding(top = 40.dp)
                        )
                        Image(
                            painter = painterResource(
                                id = SirAvatar.getResourceIdForAvatar(
                                    responseData.preview.oppInfo.avatar_key,
                                    responseData.preview.oppInfo.team_num
                                )
                            ),
                            contentDescription = "Team Avatar",
                            modifier = Modifier
                                .size(140.dp)
                                .padding(start = 20.dp, end = 20.dp)
                        )
                    }
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            text = "${responseData.preview.teamInfo.curr_wins}-${responseData.preview.teamInfo.curr_losses}",
                            modifier = Modifier
                                .width(matchupWidths[0])
                                .background(
                                    color = colorResource(
                                        R.color.score_team_bg
                                    )
                                ),
                            fontSize = 22.sp
                        )
                        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
                        Text(
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            text = "${responseData.preview.oppInfo.curr_wins}-${responseData.preview.oppInfo.curr_losses}",
                            modifier = Modifier
                                .width(matchupWidths[0])
                                .background(
                                    color = colorResource(
                                        R.color.score_opp_bg
                                    )
                                ),
                            fontSize = 22.sp
                        )
                    }

                Text(text = " ", fontSize = 10.sp)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(start = 3.dp, end = 3.dp)
                        .size(370.dp)

                ) {
                    item {
                        RenderTeamPreviewBlock(
                            responseData.preview.teamInfo, responseData.preview.slots,
                            colorResource(R.color.score_team_bg), false,
                            responseData.gamesByNflTeam
                        )
                    }
                    item {
                        RenderTeamPreviewBlock(
                            responseData.preview.teamInfo, responseData.preview.slots,
                            colorResource(R.color.score_bench_bg), true,
                            responseData.gamesByNflTeam
                        )
                    }
                    item {
                        RenderTeamPreviewBlock(
                            responseData.preview.oppInfo, responseData.preview.oppSlots,
                            colorResource(R.color.score_opp_bg), false,
                            responseData.gamesByNflTeam
                        )
                    }
                    item {
                        RenderTeamPreviewBlock(
                            responseData.preview.oppInfo, responseData.preview.oppSlots,
                            colorResource(R.color.score_bench_bg), true,
                            responseData.gamesByNflTeam
                        )
                    }
                    item {
                        Text(text = " ", fontSize = 64.sp)
                    }

                }

            }

            else -> {
                val error = loadState.toString()
                Text("Error loading team Occurred: $error")
            }
        }
    }

}


@Composable
fun RenderTeamPreviewBlock(teamInfo: PreviewTeamInfo, slots: Map<String, PreviewTeamSlot>,
                           bgColor: Color, renderBench: Boolean, gameInfo : Map<String, ScheduledGameData>) {

    val rosterType = if (renderBench) "Bench" else "Starters"
    Text(
        "${teamInfo.team_city} ${teamInfo.team_name} (${teamInfo.team_abbrev}) - $rosterType",
        fontSize = 14.sp
    )
    val displaySlots = if (renderBench) arrayOf("B1", "B2", "B3", "B4", "B5", "B6")
        else arrayOf("QB1", "RB1", "RB2", "WR1", "WR2", "TE1", "FLEX", "K1", "DFST1")


        val slotWidths = arrayOf(45.dp, 170.dp, 50.dp, 45.dp)
            for (entry in slots.entries.iterator()) {
                if (entry.value.slot_name in displaySlots) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier
                            .background(color = bgColor)
                            .fillMaxWidth()
                            .padding(
                                horizontal = 2.dp,
                                vertical = 2.dp,
                            )
                    ) {
                        val slotData = entry.value
                        val playerTeam = slotData.player?.team ?: "UNK"
                        Text(
                            textAlign = TextAlign.Center,
                            text = slotData.slot_name,
                            color = Color.White,
                            modifier = Modifier
                                .width(slotWidths[0])
                                .background(
                                    color = SirRoster.getSlotColor(slotIn = slotData.slot_name)
                                ),
                            fontSize = 14.sp
                        )
                        if (slotData.player != null) {
                            if (slotData.player.pos == "DFST") {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = slotData.player.fullName,
                                    color = Color.White,
                                    modifier = Modifier.width(slotWidths[1]),
                                    fontSize = 12.sp
                                )
                            } else {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = "${slotData.player.fullName} ${slotData.player.team} #${slotData.player.jerseyNum}",
                                    color = Color.White,
                                    modifier = Modifier.width(slotWidths[1]),
                                    fontSize = 12.sp
                                )
                            }

                        } else {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "Empty Slot",
                                color = Color.White,
                                modifier = Modifier.width(slotWidths[1]),
                                fontSize = 12.sp
                            )
                        }
                        Text(
                            textAlign = TextAlign.Center,
                            text = SirRoster.getNflGameInfo(teamAbbrevIn = playerTeam,
                                gameData = gameInfo[playerTeam]
                            ),
                            color = Color.White,
                            modifier = Modifier.width(slotWidths[2]),
                            fontSize = 14.sp
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "●●●●●".substring(0, (slotData.player?.caliber ?: 1)),
                            color = Color.White,
                            modifier = Modifier.width(slotWidths[3]),
                            fontSize = 14.sp
                        )
                    }
                }
         }

}