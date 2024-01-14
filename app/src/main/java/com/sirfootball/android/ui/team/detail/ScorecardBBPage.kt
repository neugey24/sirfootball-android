package com.sirfootball.android.ui.team.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirfootball.android.R
import com.sirfootball.android.data.model.LoadScorecardResponse
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame

@Composable
fun ScorecardBBPage(responseData : LoadScorecardResponse) {
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
            text = SirGame.formatScoreForGame(scoreIn = responseData.scorecard.oppScorecard?.teamGrandTotal, gameAbbrevIn = responseData.gameAbbrev),
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
    Row {
        Text(
            textAlign = TextAlign.Center,
            text = "Squares: ${SirGame.formatScoreForGame(scoreIn = responseData.scorecard.teamScorecard.teamSubTotal, gameAbbrevIn = responseData.gameAbbrev)}",
            modifier = Modifier
                .width(matchupWidths[0]),
            fontSize = 16.sp
        )
        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
        Text(
            textAlign = TextAlign.Center,
            text = "Squares: ${SirGame.formatScoreForGame(scoreIn = responseData.scorecard.oppScorecard?.teamSubTotal, gameAbbrevIn = responseData.gameAbbrev)}",
            modifier = Modifier
                .width(matchupWidths[0]),
            fontSize = 16.sp
        )

    }
    Row {
        Text(
            textAlign = TextAlign.Center,
            text = "Bingo: ${SirGame.formatScoreForGame(scoreIn = responseData.scorecard.teamScorecard.teamBingoPoints?.toDouble(), gameAbbrevIn = responseData.gameAbbrev)}",
            modifier = Modifier
                .width(matchupWidths[0]),
            fontSize = 16.sp
        )
        Text(text = " ", modifier = Modifier.width(matchupWidths[1]))
        Text(
            textAlign = TextAlign.Center,
            text = "Bingo: ${SirGame.formatScoreForGame(scoreIn = responseData.scorecard.oppScorecard?.teamBingoPoints?.toDouble(), gameAbbrevIn = responseData.gameAbbrev)}",
            modifier = Modifier
                .width(matchupWidths[0]),
            fontSize = 16.sp
        )

    }

    Text(" ", fontSize = 32.sp)
    Text(" (see website for full bingo scorecards) ", fontSize = 15.sp)

}




