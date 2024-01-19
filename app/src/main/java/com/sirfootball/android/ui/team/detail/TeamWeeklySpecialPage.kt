package com.sirfootball.android.ui.team.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.viewmodel.TeamWeeklySpecialViewModel

@Composable
fun TeamWeeklySpecialPage(teamId : Int) {
    val viewModel = hiltViewModel<TeamWeeklySpecialViewModel>()
    val loadState = viewModel.getWeeklySpecialResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId)
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
                val team = responseData.info
                val specialInfo = responseData.special

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
                Text("Weekly Special", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Active Special - NFL Week ${specialInfo.weekNum}", fontSize = 22.sp)
                Spacer(Modifier.weight(1f))
                Text(specialInfo.special.name, fontSize = 25.sp)
                Text(specialInfo.special.detail, fontSize = 14.sp)
                Spacer(Modifier.weight(2f))

            }


            else -> {
                val error = loadState.toString()
                Text("Error loading team Occurred: $error")
            }
        }
    }



}







