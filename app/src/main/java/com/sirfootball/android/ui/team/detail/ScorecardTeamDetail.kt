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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirfootball.android.R
import com.sirfootball.android.data.model.PennantData
import com.sirfootball.android.data.model.PickEmMatchup
import com.sirfootball.android.data.model.ScorecardPlayerInfo
import com.sirfootball.android.data.model.ScorecardTeamInfo
import com.sirfootball.android.data.model.TeamScorecard
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.structure.SirRoster

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScorecardTeamDetail(teamInfo : ScorecardTeamInfo, teamScorecard: TeamScorecard,
                        sheetValueChange: (Boolean) -> Unit, gameAbbrev: String, oppScorecard: TeamScorecard) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =
    Modifier
        .padding(horizontal = 6.dp)
        .height(640.dp)

        ) {
        Image(
            painter = painterResource(
                id = SirAvatar.getResourceIdForAvatar(
                    teamInfo.avatar_key ?: "UNK",
                    teamInfo.team_num
                )
            ),
            contentDescription = "Team Avatar",
            modifier = Modifier
                .size(140.dp)
        )

        Text("${teamInfo.team_city} ${teamInfo.team_name} (${teamInfo.team_abbrev})")
        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

        Row {
            Text( "Subtotal", fontSize = 20.sp)
            Spacer(Modifier.weight(1f))
            Text( SirGame.formatScoreForGame(scoreIn = teamScorecard.teamSubTotal, gameAbbrevIn = gameAbbrev),
                fontSize = 20.sp)
        }
        Row {
            if (gameAbbrev == "TAL") {
                Text( "Tier Rankings Points", fontSize = 16.sp)
            } else {
                Text( "Bonus", fontSize = 16.sp)
            }
            Spacer(Modifier.weight(1f))
            Text( SirGame.formatScoreForGame(scoreIn = (teamScorecard.teamGrandTotal - teamScorecard.teamSubTotal), gameAbbrevIn = gameAbbrev),
                fontSize = 16.sp)
        }
        Row {
            Text( "Grand Total", fontSize = 24.sp)
            Spacer(Modifier.weight(1f))
            Text( SirGame.formatScoreForGame(scoreIn = teamScorecard.teamGrandTotal, gameAbbrevIn = gameAbbrev),
                fontSize = 24.sp)
        }
        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
        if (gameAbbrev == "BC") {
            RenderBlessedCursedScoresheet(teamScorecard)
        }
        if (gameAbbrev == "DD1") {
            RenderDecisionsScoresheet(teamScorecard)
        }
        if (gameAbbrev == "DD2") {
            RenderDoubleDownScoresheet(teamScorecard)
        }
        if (gameAbbrev == "PP") {
            RenderPennantPlayScoresheet(teamScorecard, oppScorecard)
        }
        if (gameAbbrev == "SCS") {
            RenderScarySightScoresheet()
        }
        if (gameAbbrev == "TD") {
            RenderTopDogScoresheet(teamScorecard)
        }
        if (gameAbbrev == "UH") {
            RenderUnsungHeroScoresheet(teamScorecard)
        }
        if (gameAbbrev == "WS") {
            RenderWeeklySpecialScoresheet(teamScorecard)
        }

        Text(" ", fontSize = 36.sp)
        Text("[Dismiss Window]", fontSize = 14.sp, modifier = Modifier.clickable(onClick = {  sheetValueChange(false) }))


    }

}

@Composable
private fun RenderScarySightScoresheet() {
    Text( "Scary Sight", fontSize = 22.sp)
    Text("Lowest scoring 3 starters have points tripled.")
    Text("(Shown as bonus on team and player pages)")
}

@Composable
private fun RenderTopDogScoresheet(teamScorecard: TeamScorecard){
    Text( "Top Dog", fontSize = 22.sp)
    Text("Highest Scoring Player", fontSize = 25.sp)
    val topDogSlot = teamScorecard.topDogSlot ?: "QB1"

    val matchedPlayer = teamScorecard.slots?.get(topDogSlot)?.playerInfo ?: ScorecardPlayerInfo()
    Text("${matchedPlayer.pos} ${matchedPlayer.fullName} ${matchedPlayer.team ?: "FA"}", fontSize = 18.sp)
    Text("Points: ${SirGame.formatScoreForGame(scoreIn = teamScorecard.teamGrandTotal, gameAbbrevIn = "TD" )}", fontSize = 22.sp)

 }

@Composable
private fun RenderUnsungHeroScoresheet(teamScorecard: TeamScorecard) {
    Text( "Unsung Hero", fontSize = 22.sp)
    Text("Bonus for best bench player", fontSize = 25.sp)
    val topDogSlot = teamScorecard.topDogSlot ?: "QB1"

    val matchedPlayer = teamScorecard.unsungHero?.data?.playerInfo ?: ScorecardPlayerInfo()
    val uhPoints = teamScorecard.unsungHero?.data?.stat?.playerSubTotal ?: 0.0
    val uhTotal = teamScorecard.unsungHero?.data?.stat?.playerGrandTotal ?: 0.0
    Text("${matchedPlayer.pos} ${matchedPlayer.fullName} ${matchedPlayer.team ?: "FA"}", fontSize = 24.sp)
    Text("Total Points: ${SirGame.formatScoreForGame(scoreIn = uhPoints, gameAbbrevIn = "UH" )}", fontSize = 20.sp)
    Text("Bonus for Team: ${SirGame.formatScoreForGame(scoreIn = uhTotal, gameAbbrevIn = "UH" )}", fontSize = 22.sp)

}


@Composable
fun RenderWeeklySpecialScoresheet(teamScorecard: TeamScorecard) {
    Text( "Weekly Special", fontSize = 22.sp)
    Text("Active Special:", fontSize = 25.sp)
    Text(teamScorecard.special?.name ?: "None", fontSize = 22.sp)
    Text(teamScorecard.special?.detail ?: "None", fontSize = 15.sp)

}


@Composable
fun RenderBlessedCursedScoresheet(teamScorecard: TeamScorecard) {
    Text( "Blessed & Cursed", fontSize = 22.sp)
    Text("Active Spell: ${teamScorecard.spells?.name}", fontSize = 18.sp)
    Text( teamScorecard.spells?.detail ?: "", fontSize = 13.sp)

}

@Composable
private fun RenderDecisionsScoresheet(teamScorecard: TeamScorecard) {
    val pickPercent = "%.2f".format(teamScorecard.pickEm?.score)
    val pickCount = teamScorecard.pickEm?.matchUps?.keys?.size

    // Split pickEm data into two arrays for 2-column display
    val pickMap = teamScorecard.pickEm?.matchUps ?: emptyMap()
    var ctr = 0
    val col1Picks = mutableListOf<PickEmMatchup>()
    val col2Picks = mutableListOf<PickEmMatchup>()
    for (entry in pickMap.entries.iterator()) {
        if (ctr % 2 == 0) {
            col1Picks.add(entry.value)

        } else {
            col2Picks.add(entry.value)
        }
        ctr += 1
    }
    ctr = (ctr / 2) - 1

    Row {
        Text( "NFL Picks:", fontSize = 20.sp)
        Spacer(Modifier.weight(1f))
        Text("${teamScorecard.pickEm?.gamesCorrect}/${teamScorecard.pickEm?.gamesPresent} = $pickPercent%",
            fontSize = 20.sp)
    }

    for (cc in 0..ctr) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 2.dp,
                    vertical = 2.dp,
                )
        ) {
            RenderScoresheetPickemSinglePick(col1Picks[cc])
            RenderScoresheetPickemSinglePick(col2Picks[cc])
        }
    }

}

@Composable
private fun RenderScoresheetPickemSinglePick(pick: PickEmMatchup) {
    val pickText = if (pick.away == pick.selection) "[${pick.away}] vs ${pick.home}" else "${pick.away} vs [${pick.home}]"
    Text(
        textAlign = TextAlign.Center,
        text = pickText,
        modifier = Modifier
            .width(156.dp),
        fontSize = 16.sp
    )
    if (pick.final) {
        if (pick.correct) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dd2_correct_black_24dp),
                contentDescription = "Correct Pick",
                modifier = Modifier.size(20.dp),
                tint = colorResource(id = R.color.te_tab_bg)
            )
         } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_dd2_wrong_black_24dp),
                contentDescription = "Wrong Pick",
                modifier = Modifier.size(20.dp),
                tint = Color.Red
            )
        }
    } else {
        Icon(
            painter = painterResource(id = R.drawable.ic_dd2_question_black_24dp),
            contentDescription = "TBD Pick",
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }

}

@Composable
private fun RenderDoubleDownScoresheet(teamScorecard: TeamScorecard) {
    Text( "Double Down", fontSize = 22.sp)
    Text("Chosen Player:", fontSize = 18.sp)

    if (teamScorecard.doubleDown?.playerId == null) {
        Text("(None Chosen)", fontSize = 26.sp)
    } else {
        val playerIdToMatch = teamScorecard.doubleDown.playerId
        for (currSlot in SirRoster.getStartingSlots()) {
            if (teamScorecard.slots?.get(currSlot)?.playerInfo?.id == playerIdToMatch) {
                val matchedPlayer = teamScorecard.slots?.get(currSlot)?.playerInfo ?: ScorecardPlayerInfo()
                Text("${matchedPlayer.pos} ${matchedPlayer.fullName} ${matchedPlayer.team}", fontSize = 26.sp)
            }
        }
    }
    Text("Points: ${SirGame.formatScoreForGame(scoreIn = teamScorecard.doubleDown?.points, gameAbbrevIn = "DD2" )}")
}


@Composable
fun RenderPennantPlayScoresheet(teamScorecard: TeamScorecard, oppScorecard : TeamScorecard) {
    val teamPennant = teamScorecard.pennant ?: PennantData()
    val oppPennant = oppScorecard.pennant ?: PennantData()
    Text( "Pennant Play", fontSize = 22.sp)
    Text("Active Pennants:", fontSize = 18.sp)

    if (teamPennant.name != "" && teamPennant.type == "positive") {
        Text(" ", fontSize = 26.sp)
        Text(teamPennant.name, fontWeight = FontWeight.Bold)
        Text("${teamPennant.detail} [played by your team]", fontSize = 12.sp)
    }

    if (oppPennant.name != "" && oppPennant.type == "negative") {
        Text(" ", fontSize = 26.sp)
        Text(oppPennant.name, fontWeight = FontWeight.Bold)
        Text("${oppPennant.detail} [played by opponent]", fontSize = 12.sp)
    }

    if ((teamPennant.name != "" && teamPennant.type == "negative") && (oppPennant.name != "" && oppPennant.type == "positive")) {
        Text("No pennants affecting team", fontWeight = FontWeight.Bold)
    }

}



