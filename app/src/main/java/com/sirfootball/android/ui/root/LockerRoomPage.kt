package com.sirfootball.android.ui.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirfootball.android.viewmodel.GetTeamsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.UserTeam

@Composable
fun LockerRoomPage() {
    val viewModel = hiltViewModel<GetTeamsViewModel>()
    val loadState = viewModel.getTeamsResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }
    val padTop = Modifier.padding(top = 25.dp)
    Row(verticalAlignment = Alignment.Top, modifier = padTop) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home_black_24dp),
            contentDescription = "Locker Room",
            modifier = Modifier.size(46.dp)
        )
        Text(text = " Your Teams", fontSize = 36.sp)
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
                if (responseData.leaguesAvailable) {
                    if (responseData.userTeams.isEmpty()) {
                        Text(text = "You have no teams yet.", fontSize = 26.sp)
                        Text("")
                        Text("Click on the Add (+) button to find a league.")
                    } else {
                       // Text("$responseData.userTeams.size found - TBD list teams")
                        Text("Teams found - TBD list teams")
                        for (teamInfo in responseData.userTeams ) {
                            RenderTeamBlock(teamInfo)
                        }

                    }
                } else {
                    Text(text = "Leagues not yet available", fontSize = 26.sp)
                    Text("")
                    Text("It's the offseason!  Please visit sirfootball.com on laptop/PC for game information.")
                }
            }

            else -> {
                val error = loadState.toString()
                Text("Error loading teamsOccurred: $error")
            }
        }

    } // end column

}

@Composable
fun RenderTeamBlock(teamInfo: UserTeam) {
    Text(teamInfo.team_name)
}