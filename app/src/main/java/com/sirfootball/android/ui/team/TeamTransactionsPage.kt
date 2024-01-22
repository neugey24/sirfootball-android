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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.TransactionInfo
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.viewmodel.GetTeamTransactionsViewModel


@Composable
fun TeamTransactionsPage(teamId : Int) {
    val viewModel = hiltViewModel<GetTeamTransactionsViewModel>()
    val loadState = viewModel.getTeamTransactionsResponse.value

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
            val tran = responseData.tran

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
                Text(
                    text = "Team Transactions", fontSize = 28.sp,
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
                            RenderTeamTransaction(currentTran)

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
private fun RenderTeamTransaction(currentTran: TransactionInfo) {

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

