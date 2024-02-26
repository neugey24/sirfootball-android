package com.sirfootball.android.ui.team.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirfootball.android.data.model.ScorecardPlayerInfo
import com.sirfootball.android.data.model.ScorecardStat
import com.sirfootball.android.data.model.ScorecardTeamInfo
import com.sirfootball.android.data.model.StatRecord
import com.sirfootball.android.data.model.TeamScorecard
import com.sirfootball.android.structure.SirGame

@Composable
fun ScorecardPlayerDetail(teamInfo : ScorecardTeamInfo,
                          currentDetailPlayer: ScorecardPlayerInfo,
                          currentDetailPlayerStat: ScorecardStat,
                        sheetValueChange: (Boolean) -> Unit, gameAbbrev: String,
                          teamScorecard: TeamScorecard,
                          oppScorecard: TeamScorecard) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =
    Modifier
        .padding(horizontal = 6.dp)
        .height(640.dp)

        ) {

        Text("${teamInfo.team_city} ${teamInfo.team_name} (${teamInfo.team_abbrev})", fontSize = 15.sp)

        if (currentDetailPlayer.pos == "DFST") {
            Text(
                textAlign = TextAlign.Center,
                text = currentDetailPlayer.fullName,
                fontSize = 18.sp
            )
        } else {
            Text(
                textAlign = TextAlign.Center,
                text = "${currentDetailPlayer.fullName} ${currentDetailPlayer.team} #${currentDetailPlayer.jerseyNum}",
                fontSize = 18.sp
            )
        }
        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
        for (statName in SirGame.ALL_STATS) {
            val currStatRecord = currentDetailPlayerStat.statSet?.get(statName)
            if (currStatRecord != null) {
                val currValue = "%.2f".format(currStatRecord.value)
                val currTotal = "%.2f".format(currStatRecord.total)
                Row {
                    Column {
                        Text("${currStatRecord.amount} ${currStatRecord.brief} ($currValue each)", fontSize = 12.sp)
                        RenderPlayerStatModifiers(currStatRecord, gameAbbrev)

                    }
                    Spacer(Modifier.weight(1f))
                    Text(currTotal, fontSize = 18.sp)
                }
                Text(" ", fontSize = 3.sp)
            }
        }

        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

        Row {
            Text( "Subtotal", fontSize = 20.sp)
            Spacer(Modifier.weight(1f))
            Text( SirGame.formatScoreForGame(scoreIn = currentDetailPlayerStat.playerSubTotal, gameAbbrevIn = gameAbbrev),
                fontSize = 20.sp)
        }
        Row {
            if (gameAbbrev == "TAL") {
                val tierLevel = if (currentDetailPlayerStat.modifiers?.size!! > 0)
                    currentDetailPlayerStat.modifiers[0] else "UNK"
                Text("$tierLevel Points", fontSize = 16.sp)
            } else {
                Text("Bonus", fontSize = 16.sp)
            }
            Spacer(Modifier.weight(1f))
            Text( SirGame.formatScoreForGame(scoreIn = (currentDetailPlayerStat.playerGrandTotal - currentDetailPlayerStat.playerSubTotal), gameAbbrevIn = gameAbbrev),
                fontSize = 16.sp)
        }
        Row {
            Text( "Grand Total", fontSize = 24.sp)
            Spacer(Modifier.weight(1f))
            Text( SirGame.formatScoreForGame(scoreIn = currentDetailPlayerStat.playerGrandTotal, gameAbbrevIn = gameAbbrev),
                fontSize = 24.sp)
        }
        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
        if (gameAbbrev=="BC") {
            RenderBlessedCursedScoresheet(teamScorecard)
        }
        if (gameAbbrev == "PP") {
            RenderPennantPlayScoresheet(teamScorecard, oppScorecard)
        }
        if (gameAbbrev == "WS") {
            RenderWeeklySpecialScoresheet(teamScorecard)
        }

        Text(" ", fontSize = 8.sp)
        Text("[Dismiss Window]", fontSize = 14.sp, modifier = Modifier.clickable(onClick = {  sheetValueChange(false) }))


    }

}

@Composable
fun RenderPlayerStatModifiers(currStatRecord: StatRecord, gameAbbrev: String) {
    if (!currStatRecord.modifiers.isNullOrEmpty()) {
        var modDisplay = ""
        for (currMod in currStatRecord.modifiers) {
            if (gameAbbrev == "PP") {
            modDisplay += " [${currMod.replace(" Pennant", "")}] "
            } else {
                modDisplay += " [$currMod] "
            }
        }
        Text(modDisplay, fontSize = 12.sp)
    } else {
        Text("[Standard]", fontSize = 12.sp)
    }

}

