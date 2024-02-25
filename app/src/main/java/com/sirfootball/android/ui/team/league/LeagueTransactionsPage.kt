package com.sirfootball.android.ui.team.league

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.LeagueTransactionInfo
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.viewmodel.GetLeagueTransactionsViewModel


@Composable
fun LeagueTransactionsPage(leagueId : Int) {
    val viewModel = hiltViewModel<GetLeagueTransactionsViewModel>()
    val loadState = viewModel.getLeagueTransactionsResponse.value

    var teamCount = 0

    LaunchedEffect(Unit) {
        viewModel.fetch(leagueId)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text("Loading Data ...")
        }

        is ApiState.Success -> {
            val responseData = loadState.data
            val info = responseData.info
            val tran = responseData.trans

            val gameLogoId = LocalContext.current.resources.getIdentifier(
                "game_logo_${SirGame.GAME_DATA[info.leagueInfo.game_abbrev]?.get("logo")}", "drawable",
                LocalContext.current.packageName)

            val padTop = Modifier.padding(top = 25.dp)
            Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                Image(
                    painter = painterResource( id = gameLogoId),
                    contentDescription = "Game Type",
                    modifier = Modifier.size(100.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${SirGame.GAME_DATA[info.leagueInfo.game_abbrev]?.get("name")}", fontSize = 24.sp)
                    Text(text = info.leagueInfo.league_name, fontSize = 16.sp)
                }

            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "League Transactions", fontSize = 28.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "(Newest to Oldest)", fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(408.dp)
                ) {
                    for (currentTran in tran.transactions) {
                        item {
                            RenderLeagueTransaction(currentTran)

                        }
                    }

                }
            }
        }

        else -> {
            val error = loadState.toString()
            Text("Error loading league standings occurred: $error")
        }
    }

}

@Composable
private fun RenderLeagueTransaction(currentTran: LeagueTransactionInfo) {

    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.panel_bg))
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            )
    ) {
        Text(
            textAlign = TextAlign.Left,
            text = "Team: ${currentTran.fantasy_team_abbrev}",
            color = colorResource(R.color.panel_fg),
            fontSize = 23.sp
        )
        Text(
            textAlign = TextAlign.Left,
            text = "${currentTran.fantasy_position} ${currentTran.full_name} (${currentTran.team_abbrev})",
            color = colorResource(R.color.panel_fg),
            fontSize = 20.sp
        )
        Text(
            textAlign = TextAlign.Left,
            text = "Type: ${currentTran.tran_type}",
            color = colorResource(R.color.panel_fg),
            fontSize = 18.sp
        )
        Text(
            textAlign = TextAlign.Left,
            text = "Date: ${currentTran.created_date}",
            color = colorResource(R.color.panel_fg),
            fontSize = 16.sp
        )

    }

}
