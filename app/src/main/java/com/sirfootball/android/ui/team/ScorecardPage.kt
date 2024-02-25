package com.sirfootball.android.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.ScheduledGameData
import com.sirfootball.android.data.model.ScorecardPlayerInfo
import com.sirfootball.android.data.model.ScorecardStat
import com.sirfootball.android.data.model.ScorecardTeamInfo
import com.sirfootball.android.data.model.TeamScorecard
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster
import com.sirfootball.android.ui.team.detail.ScorecardBBPage
import com.sirfootball.android.ui.team.detail.ScorecardPlayerDetail
import com.sirfootball.android.ui.team.detail.ScorecardSLPage
import com.sirfootball.android.ui.team.detail.ScorecardTeamDetail
import com.sirfootball.android.viewmodel.GetScorecardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScorecardPage(leagueId : Int, weekNum: Int, matchupNum: Int) {
    val viewModel = hiltViewModel<GetScorecardViewModel>()
    val loadState = viewModel.getScorecardResponse.value

    val sheetUp = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    var showTeamDetail by remember { mutableStateOf(false) }
    var currentDetailTeam by remember { mutableStateOf(ScorecardTeamInfo()) }
    var currentDetailPlayer by remember { mutableStateOf(ScorecardPlayerInfo()) }
    var currentDetailTeamScorecard by remember { mutableStateOf(TeamScorecard()) }
    var currentDetailPlayerStat by remember { mutableStateOf(ScorecardStat()) }
    var oppScorecard by remember { mutableStateOf(TeamScorecard()) }

    var gameAbbrev = "UNK"

    LaunchedEffect(Unit) {
        viewModel.fetch(leagueId, weekNum, matchupNum)
    }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize())
    {
        when (loadState) {
            is ApiState.Loading -> {
                Text("Loading Data ...")
            }

            is ApiState.Success -> {
                val responseData = loadState.data
                gameAbbrev = responseData.gameAbbrev
                val gameLogoId = LocalContext.current.resources.getIdentifier(
                    "game_logo_${SirGame.GAME_DATA[responseData.gameAbbrev]?.get("logo")}",
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
                                SirGame.GAME_DATA[responseData.gameAbbrev]?.get(
                                    "name"
                                )
                            }", fontSize = 24.sp
                        )
                        Text(
                            text = responseData.scorecard.teamInfo.league_name,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "${responseData.scorecard.matchupTitle}, Week ${responseData.scorecard.weekNum}",
                            fontSize = 14.sp
                        )
                    }

                }
                Text(text = " ", fontSize = 10.sp)

                if (gameAbbrev == "BB" || gameAbbrev == "SL") {
                    if (gameAbbrev == "BB") {
                        ScorecardBBPage(responseData)
                    } else {
                        ScorecardSLPage(responseData)
                    }
                } else {

                    // Matchup Info
                    val matchupWidths = arrayOf(180.dp, 70.dp)
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.scorecard.teamInfo.team_city,
                            modifier =
                            Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.scorecard.oppInfo?.team_city ?: "",
                            modifier = Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.scorecard.teamInfo.team_name,
                            modifier = Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
                        Text(
                            textAlign = TextAlign.Center,
                            text = responseData.scorecard.oppInfo?.team_name ?: "",
                            modifier = Modifier.width(matchupWidths[0]),
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        Image(
                            painter = painterResource(
                                id = SirAvatar.getResourceIdForAvatar(
                                    responseData.scorecard.teamInfo.avatar_key ?: "UNK",
                                    responseData.scorecard.teamInfo.team_num
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
                                    responseData.scorecard.oppInfo?.avatar_key ?: "UNK",
                                    responseData.scorecard.oppInfo?.team_num ?: 1
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
                            text = SirGame.formatScoreForGame(scoreIn = responseData.scorecard.teamScorecard.teamGrandTotal, gameAbbrevIn = responseData.gameAbbrev),
                            modifier = Modifier
                                .width(100.dp)
                                .background(
                                    color = colorResource(
                                        R.color.score_team_bg
                                    )
                                ),
                            fontSize = 22.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_stickynote_black_24dp),
                            contentDescription = "Scorecard Details",
                            modifier = Modifier.size(28.dp).clickable(
                                onClick = {
                                    showTeamDetail = true
                                    currentDetailTeam = responseData.scorecard.teamInfo
                                    currentDetailTeamScorecard = responseData.scorecard.teamScorecard
                                    oppScorecard = responseData.scorecard.oppScorecard ?: TeamScorecard()
                                    showBottomSheet = true

                                }
                            )
                        )
                        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
                        Text(
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            text = SirGame.formatScoreForGame(scoreIn = responseData.scorecard.oppScorecard?.teamGrandTotal, gameAbbrevIn = responseData.gameAbbrev),
                            modifier = Modifier
                                .width(100.dp)
                                .background(
                                    color = colorResource(
                                        R.color.score_opp_bg
                                    )
                                ),
                            fontSize = 22.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_stickynote_black_24dp),
                            contentDescription = "Scorecard Details",
                            modifier = Modifier.size(28.dp).clickable(
                                onClick = {
                                    showTeamDetail = true
                                    currentDetailTeam = responseData.scorecard.oppInfo ?: ScorecardTeamInfo()
                                    currentDetailTeamScorecard = responseData.scorecard.oppScorecard ?: TeamScorecard()
                                    oppScorecard = responseData.scorecard.teamScorecard
                                    showBottomSheet = true

                                }
                            )

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
                            RenderTeamScorecardBlock(
                                gameAbbrev = responseData.gameAbbrev,
                                teamInfo = responseData.scorecard.teamInfo,
                                card = responseData.scorecard.teamScorecard,
                                bgColor = colorResource(R.color.score_team_bg),
                                renderBench = false,
                                gameInfo = responseData.gamesByNflTeam,
                                sheetValueChange = {showBottomSheet = it},
                                oppScorecardPass = {oppScorecard = it},
                                showTeamDetail = {showTeamDetail = it},
                                currDetailTeam = {currentDetailTeam = it},
                                oppScorecardIn = responseData.scorecard.oppScorecard ?: TeamScorecard(),
                                scorecardPass = {currentDetailTeamScorecard = it},
                                currentDetailPlayerPass = {currentDetailPlayer = it},
                                currentDetailPlayerStatPass = {currentDetailPlayerStat = it}
                            )
                        }
                        item {
                            RenderTeamScorecardBlock(
                                gameAbbrev = responseData.gameAbbrev,
                                teamInfo = responseData.scorecard.teamInfo,
                                card = responseData.scorecard.teamScorecard,
                                bgColor = colorResource(R.color.score_bench_bg),
                                renderBench = true,
                                gameInfo = responseData.gamesByNflTeam,
                                sheetValueChange = {showBottomSheet = it},
                                oppScorecardPass = {oppScorecard = it},
                                showTeamDetail = {showTeamDetail = it},
                                currDetailTeam = {currentDetailTeam = it},
                                oppScorecardIn = responseData.scorecard.oppScorecard ?: TeamScorecard(),
                                scorecardPass = {currentDetailTeamScorecard = it},
                                currentDetailPlayerPass = {currentDetailPlayer = it},
                                currentDetailPlayerStatPass = {currentDetailPlayerStat = it}
                            )
                        }
                        if (responseData.scorecard.oppScorecard != null) {
                            item {
                                RenderTeamScorecardBlock(
                                    gameAbbrev = responseData.gameAbbrev,
                                    teamInfo = responseData.scorecard.oppInfo ?: ScorecardTeamInfo(),
                                    card = responseData.scorecard.oppScorecard!!,
                                    bgColor = colorResource(R.color.score_opp_bg),
                                    renderBench = false,
                                    gameInfo = responseData.gamesByNflTeam,
                                    sheetValueChange = {showBottomSheet = it},
                                    oppScorecardPass = {oppScorecard = it},
                                    showTeamDetail = {showTeamDetail = it},
                                    currDetailTeam = {currentDetailTeam = it},
                                    oppScorecardIn = responseData.scorecard.teamScorecard,
                                    scorecardPass = {currentDetailTeamScorecard = it},
                                    currentDetailPlayerPass = {currentDetailPlayer = it},
                                    currentDetailPlayerStatPass = {currentDetailPlayerStat = it}
                                )
                            }
                            item {
                                RenderTeamScorecardBlock(
                                    gameAbbrev = responseData.gameAbbrev,
                                    teamInfo = responseData.scorecard.oppInfo ?: ScorecardTeamInfo(),
                                    card = responseData.scorecard.oppScorecard!!,
                                    bgColor = colorResource(R.color.score_bench_bg),
                                    renderBench = true,
                                    gameInfo = responseData.gamesByNflTeam,
                                    sheetValueChange = {showBottomSheet = it},
                                    oppScorecardPass = {oppScorecard = it},
                                    showTeamDetail = {showTeamDetail = it},
                                    currDetailTeam = {currentDetailTeam = it},
                                    oppScorecardIn = responseData.scorecard.teamScorecard,
                                    scorecardPass = {currentDetailTeamScorecard = it},
                                    currentDetailPlayerPass = {currentDetailPlayer = it},
                                    currentDetailPlayerStatPass = {currentDetailPlayerStat = it}
                                )
                            }
                            item {
                                Text(text = " ", fontSize = 64.sp)
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

        if (showBottomSheet) {
            ModalBottomSheet(
                containerColor = colorResource(R.color.panel_bg),
                contentColor = colorResource(R.color.panel_fg),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetUp
            ) {

                if (showTeamDetail) {
                    ScorecardTeamDetail(currentDetailTeam, currentDetailTeamScorecard,
                        sheetValueChange = {showBottomSheet = it}, gameAbbrev = gameAbbrev, oppScorecard  )


                } else {
                    ScorecardPlayerDetail(currentDetailTeam, currentDetailPlayer, currentDetailPlayerStat,
                        sheetValueChange = {showBottomSheet = it}, gameAbbrev = gameAbbrev, currentDetailTeamScorecard, oppScorecard  )
                }

            }
        }
    }



}


@Composable
fun RenderTeamScorecardBlock(gameAbbrev: String,
                             teamInfo: ScorecardTeamInfo,
                             card: TeamScorecard,
                             bgColor: Color,
                             renderBench: Boolean,
                             gameInfo : Map<String, ScheduledGameData>,
                             sheetValueChange: (Boolean) -> Unit,
                             oppScorecardPass: (TeamScorecard) -> Unit,
                             showTeamDetail: (Boolean) -> Unit,
                             currDetailTeam: (ScorecardTeamInfo) -> Unit,
                             oppScorecardIn: TeamScorecard,
                             scorecardPass: (TeamScorecard) -> Unit,
                             currentDetailPlayerPass: (ScorecardPlayerInfo) -> Unit,
                             currentDetailPlayerStatPass: (ScorecardStat) -> Unit,
                             ) {

    val rosterType = if (renderBench) "Bench" else "Starters"
    Text(
        "${teamInfo.team_city} ${teamInfo.team_name} (${teamInfo.team_abbrev}) - $rosterType",
        fontSize = 14.sp
    )
    val displaySlots = if (renderBench) SirRoster.getBenchSlots() else SirRoster.getStartingSlots()

        val slotMap = card.slots ?: emptyMap()
        val slotWidths = arrayOf(45.dp, 150.dp, 40.dp, 75.dp, 20.dp)
            for (entry in slotMap.entries.iterator()) {
                if (entry.key in displaySlots) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .background(color = bgColor)
                            .fillMaxWidth()
                            .padding(
                                horizontal = 2.dp,
                                vertical = 2.dp,
                            )
                    ) {
                        val slotData = entry.value
                        val playerTeam = slotData.playerInfo?.team ?: "UNK"
                        Text(
                            textAlign = TextAlign.Center,
                            text = entry.key,
                            color = Color.White,
                            modifier = Modifier
                                .width(slotWidths[0])
                                .background(
                                    color = SirRoster.getSlotColor(slotIn = entry.key)
                                ),
                            fontSize = 14.sp
                        )
                        if (slotData.playerInfo != null) {
                            if (slotData.playerInfo.pos == "DFST") {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = slotData.playerInfo.fullName,
                                    color = Color.White,
                                    modifier = Modifier.width(slotWidths[1]),
                                    fontSize = 11.sp
                                )
                            } else {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = "${slotData.playerInfo.fullName} ${slotData.playerInfo.team} #${slotData.playerInfo.jerseyNum}",
                                    color = Color.White,
                                    modifier = Modifier.width(slotWidths[1]),
                                    fontSize = 11.sp
                                )
                            }
                        } else {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "Empty Slot",
                                color = Color.White,
                                modifier = Modifier.width(slotWidths[1]),
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            textAlign = TextAlign.Center,
                            text = SirGame.formatScoreForGame(scoreIn = slotData.stat.playerGrandTotal,
                                gameAbbrevIn = gameAbbrev),
                            color = Color.White,
                            modifier = Modifier.width(slotWidths[2]),
                            fontSize = 13.sp
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = SirRoster.getNflGameProgress(
                                gameData = gameInfo[playerTeam]
                            ),
                            color = Color.White,
                            modifier = Modifier.width(slotWidths[3]),
                            fontSize = 12.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_stickynote_black_24dp),
                            contentDescription = "Scorecard Details",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp).clickable( onClick = {
                                showTeamDetail(false)
                                currDetailTeam(teamInfo)
                                scorecardPass(card)
                                oppScorecardPass(oppScorecardIn)
                                sheetValueChange(true)
                                currentDetailPlayerPass(slotData.playerInfo ?: ScorecardPlayerInfo())
                                currentDetailPlayerStatPass(slotData.stat)
                            })

                        )
                    }
                }
         }

}
