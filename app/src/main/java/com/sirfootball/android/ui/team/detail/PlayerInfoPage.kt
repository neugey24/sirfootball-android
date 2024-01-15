package com.sirfootball.android.ui.team.detail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.LoadScorecardResponse
import com.sirfootball.android.data.model.PlayerInfoHolder
import com.sirfootball.android.data.model.PreviewTeamInfo
import com.sirfootball.android.data.model.PreviewTeamSlot
import com.sirfootball.android.data.model.ScheduledGameData
import com.sirfootball.android.data.model.ScorecardPlayerInfo
import com.sirfootball.android.data.model.ScorecardStat
import com.sirfootball.android.data.model.ScorecardTeamInfo
import com.sirfootball.android.data.model.TeamScorecard
import com.sirfootball.android.data.model.WeekByWeekStats
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.ui.team.RenderTeamScorecardBlock
import com.sirfootball.android.viewmodel.GetPlayerInfoViewModel
import com.sirfootball.android.viewmodel.GetPreviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInfoPage(playerId : Int) {
    val viewModel = hiltViewModel<GetPlayerInfoViewModel>()
    val loadState = viewModel.getPlayerInfoResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(playerId)
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
                val playerInfo = responseData.info

                val padTop = Modifier.padding(top = 18.dp)
                // Heading
                Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                    Image(
                        painter = painterResource(id = R.drawable.logo4),
                        contentDescription = "Player Info",
                        modifier = Modifier.size(110.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Player Profile", fontSize = 13.sp)
                        val playerName = if (playerInfo.playerInfo.pos == "DFST") playerInfo.playerInfo.fullName else
                            "${playerInfo.playerInfo.pos} ${playerInfo.playerInfo.fullName} #${playerInfo.playerInfo.jerseyNum}"
                        Text(
                            text = playerName,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Caliber: " + ("●●●●●".substring(0, playerInfo.playerInfo.caliber)), fontSize = 14.sp
                        )
                        if (playerInfo.playerInfo.team == null) {
                            Text(
                                text = "Free Agent",
                                fontSize = 14.sp
                            )
                        } else {
                            Text(
                                text = "${playerInfo.playerInfo.teamCity} ${playerInfo.playerInfo.teamName} (${playerInfo.playerInfo.team}) - Bye ${playerInfo.playerInfo.bye}",
                                fontSize = 14.sp
                            )
                        }

                        Text(
                            text = renderPlayerThisWeek(playerInfo),
                            fontSize = 14.sp
                        )
                    }

                }
                Text(text = "Total Fantasy Points: ${playerInfo.playerInfo.fpoints ?: 0}", fontSize = 20.sp)
                Text(text = "Fantasy Points Per Game: ${playerInfo.playerInfo.fppg ?: 0}", fontSize = 20.sp)
                Divider()


                val wbwsField = loadWbwsDataFields(pos = playerInfo.playerInfo.pos, wbwsData = playerInfo.playerInfo.wbws,
                    genData = playerInfo)

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding( start = 8.dp, end = 8.dp)
                        .size(570.dp)

                ) {
                    if (playerInfo.playerInfo.pos != "DFST") {
                        item {
                            if ((playerInfo.playerInfo.inj?.status ?: "") != "") {
                                Text(
                                    text = "Injury Status: ${playerInfo.playerInfo.inj?.status}",
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "${playerInfo.playerInfo.inj?.notes} (${playerInfo.playerInfo.inj?.date})",
                                    fontSize = 12.sp
                                )
                            } else {
                                Text(
                                    text = "Injury Status: None",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }

                    item {
                        Text("This Season's Stats", fontSize = 18.sp)
                        Text(playerInfo.playerInfo.tss?.brief ?: "Not Available", fontSize = 15.sp)
                    }

                    item {
                        Text("Last Season's Stats", fontSize = 18.sp)
                        Text(playerInfo.playerInfo.lss?.brief ?: "Not Available", fontSize = 15.sp)
                    }

                    item {
                        Text("Week by Week Stats", fontSize = 18.sp)
                        RenderWbwsHeader(pos = playerInfo.playerInfo.pos)
                    }

                    var startIndex = 0

                    for (xx in 1..18) {
                        item {
                            Row {
                                for (xy in startIndex..(startIndex+6)) {
                                    if (xy < 126) {
                                        Text(wbwsField[xy])
                                        if (xy <(startIndex+6)) {
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                }
                                startIndex += 7
                            }
                        }

                    }

                    item {
                        Text(" ", fontSize = 72.sp)
                    }
                    item {
                        Text(" ", fontSize = 72.sp)
                    }


                } // End LazyColumn


            }

            else -> {
                val error = loadState.toString()
                Text("Error loading team Occurred: $error")
            }
        }
    }

}

private fun loadWbwsDataFields(pos : String, wbwsData : Map<String, WeekByWeekStats>?, genData : PlayerInfoHolder) : List<String> {
    val fullResult = emptyList<String>().toMutableList()
    if (wbwsData == null) {
        return fullResult
    }

    when(pos) {
        "QB" -> fullResult += listOf("Opponent", "Yards", "TD", "Int", "Yards", "TD", "YPC")
        "K" -> fullResult += listOf("Opponent", "Att", "< 50", "50+", "Pct", "Att", "Made")
        "DFST" -> fullResult += listOf("Opponent", "Sacks", "Int", "FR", "PA", "SFT", "TD")
        else -> fullResult += listOf("Opponent", "Yards", "TD", "YPR", "Yards", "TD", "YPC")
    }

    for (wk in 1..18) {
        if (genData.gamesByWeek.containsKey(wk.toString())) {

            val matchup = genData.gamesByWeek[wk.toString()]
            val firstCol : String
            if (matchup?.status == "Bye") {
                firstCol = "Wk $wk - Bye"
            } else {
                val vsAt = if (matchup?.homeGame == true) "vs " else "at "
                val oppDisp = matchup?.opponent
                firstCol = "Wk $wk $vsAt ${oppDisp?.abbrev ?: "X"}"
            }

            val currData = wbwsData[wk.toString()]
            if (currData != null) {
                when (pos) {
                    "QB" -> fullResult += listOf(
                        firstCol, currData.pass?.yards.toString(), currData.pass?.td.toString(),
                        currData.pass?.int.toString(), currData.rush?.yards.toString(),
                        currData.rush?.td.toString(), currData.rush?.ypc.toString()
                    )

                    "K" -> fullResult += listOf(
                        firstCol,
                        currData.fg?.att.toString(),
                        currData.fg?.made_under50.toString(),
                        currData.fg?.made_50plus.toString(),
                        currData.fg?.pct.toString() + "%",
                        currData.xp?.att.toString(),
                        currData.xp?.made.toString()
                    )

                    "DFST" -> fullResult += listOf(
                        firstCol, currData.sacks.toString(), currData.int.toString(),
                        currData.fumRecov.toString(), currData.pa.toString(),
                        currData.safeties.toString(), currData.pa.toString()
                    )

                    else -> fullResult += listOf(
                        firstCol, currData.rec?.yards.toString(), currData.rec?.td.toString(),
                        currData.rec?.ypr.toString(), currData.rush?.yards.toString(),
                        currData.rush?.td.toString(), currData.rush?.ypc.toString()
                    )
                }
            } else {
                fullResult += listOf(
                    firstCol, "", "", "", "", "", "")
            }
        }

    }
    return fullResult

}

@Composable
private fun RenderWbwsHeader(pos : String) {
    var headerNames = emptyList<String>()
    when(pos) {
        "QB" -> headerNames = listOf("Week &", "Passing", "Rushing")
        "K" -> headerNames = listOf("Week &", "Field Goals", "Extra Points")
        "DFST" -> headerNames = listOf("Week &", "Defense", "Scoring")
        else -> headerNames = listOf("Week &", "Receiving", "Rushing")
    }
    Row {
        Spacer(Modifier.weight(1f))
        Text(headerNames[0])
        Spacer(Modifier.weight(1f))
        Text(headerNames[1])
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.weight(1f))
        Text(headerNames[2])
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun renderPlayerThisWeek(playerInfo: PlayerInfoHolder): String {
    val thisWeekGame = playerInfo.gamesByWeek[playerInfo.weekNumber.toString()]
    if (thisWeekGame == null) {
        return "Crud"
    } else {
        if (thisWeekGame.status == "Bye") {
            return "Week ${playerInfo.weekNumber}: Bye Week "
        }
        else {
            val vsAt = if (thisWeekGame.homeGame) "vs" else "at"
            val oppDisp = thisWeekGame.opponent
            return "Week ${playerInfo.weekNumber}: $vsAt ${oppDisp?.abbrev} - ${thisWeekGame.happyTime} CST"
        }
    }
}





