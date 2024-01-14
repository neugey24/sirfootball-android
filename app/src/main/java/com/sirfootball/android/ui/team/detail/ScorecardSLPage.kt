package com.sirfootball.android.ui.team.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirfootball.android.R
import com.sirfootball.android.data.model.LoadScorecardResponse
import com.sirfootball.android.data.model.ScorecardPlayerInfo
import com.sirfootball.android.data.model.ScorecardStat
import com.sirfootball.android.data.model.ScorecardTeamInfo
import com.sirfootball.android.data.model.TeamScorecard
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.ui.team.RenderTeamScorecardBlock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScorecardSLPage(responseData : LoadScorecardResponse) {

    val sheetUp = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    var showTeamDetail by remember { mutableStateOf(false) }
    var currentDetailTeam by remember { mutableStateOf(ScorecardTeamInfo()) }
    var currentDetailPlayer by remember { mutableStateOf(ScorecardPlayerInfo()) }
    var currentDetailTeamScorecard by remember { mutableStateOf(TeamScorecard()) }
    var currentDetailPlayerStat by remember { mutableStateOf(ScorecardStat()) }
    var oppScorecard by remember { mutableStateOf(TeamScorecard()) }

    // Matchup Info
    val matchupWidths = arrayOf(180.dp, 70.dp)

        Text(
            textAlign = TextAlign.Center,
            text = responseData.scorecard.teamInfo.team_city,
            modifier =
            Modifier.width(matchupWidths[0]),
            fontSize = 14.sp
        )

        Text(
            textAlign = TextAlign.Center,
            text = responseData.scorecard.teamInfo.team_name,
            modifier = Modifier.width(matchupWidths[0]),
            fontSize = 14.sp
        )


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
            textAlign = TextAlign.Center,
            color = Color.White,
            text = SirGame.formatScoreForGame(
                scoreIn = responseData.scorecard.teamScorecard.teamGrandTotal,
                gameAbbrevIn = responseData.gameAbbrev
            ),
            modifier = Modifier
                .width(100.dp)
                .background(
                    color = colorResource(
                        R.color.score_team_bg
                    )
                ),
            fontSize = 22.sp
        )

    Text(text = " ", fontSize = 10.sp)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp)
            .size(370.dp)

    ) {
        item {
            RenderTeamScorecardBlock(
                responseData.gameAbbrev,
                responseData.scorecard.teamInfo,
                responseData.scorecard.teamScorecard,
                colorResource(R.color.score_team_bg), false,
                responseData.gamesByNflTeam,
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
                responseData.gameAbbrev,
                responseData.scorecard.teamInfo, responseData.scorecard.teamScorecard,
                colorResource(R.color.score_bench_bg), true,
                responseData.gamesByNflTeam,
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




