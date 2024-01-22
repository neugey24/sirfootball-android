package com.sirfootball.android.ui.team.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.GamePickData
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.ScheduledGameData
import com.sirfootball.android.data.model.SpellData
import com.sirfootball.android.structure.AvatarGroup
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.GetTeamInfoViewModel
import com.sirfootball.android.viewmodel.TeamPickEmListViewModel
import com.sirfootball.android.viewmodel.TeamSpellsListViewModel

@Composable
fun TeamChangeAvatarSelectGroupPage(navController: NavHostController, teamId : Int) {
    val viewModel = hiltViewModel<GetTeamInfoViewModel>()
    val loadState = viewModel.getTeamInfoResponse.value

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
                val team = responseData.teamInfo

                val padTop = Modifier.padding(top = 25.dp, start = 15.dp, end = 15.dp)
                Row(verticalAlignment = Alignment.Top, modifier = padTop) {
                    Image(
                        painter = painterResource(
                            id = SirAvatar.getResourceIdForAvatar(
                                team.avatar_key,
                                team.team_num
                            )
                        ),
                        contentDescription = "Team Avatar",
                        modifier = Modifier.size(140.dp)
                    )
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = team.team_city, fontSize = 28.sp)
                        Text(text = team.team_name, fontSize = 28.sp)
                        Text(text = "(${team.team_abbrev})", fontSize = 24.sp)
                    }

                }
                Text("Change Avatar", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Select Avatar Group:", fontSize = 22.sp)
                Spacer(Modifier.weight(1f))

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color =
                        colorResource(R.color.panel_bg)
                    )
                    .size(480.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                    for (entry in AvatarGroup.entries) {

                        item {
                            Text(" ", fontSize = 8.sp)
                            Button(contentPadding = PaddingValues(all = 2.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                                    contentColor = Color.White),
                                modifier = Modifier.size(width = 360.dp, height = 34.dp),
                                onClick = {
                                    navController.navigate(
                                        TeamRoutes.TEAM_CHANGE_AVATAR_SELECT_AVATAR.
                                        replace(TeamRoutes.ARG_TAG_TEAM_ID, team.team_id.toString()).
                                        replace(TeamRoutes.ARG_TAG_AVATAR_GROUP, entry.toString()))

                                }
                            ) {
                                Text(entry.toString(), fontSize = 18.sp)
                            }
                        }
                    }
                    item {
                        Text(" ", fontSize = 50.sp)
                    }
                    item {
                        Text(" ", fontSize = 50.sp)
                    }

                }
            }


            else -> {
                val error = loadState.toString()
                Text("Error loading team Occurred: $error")
            }
        }
    }


}







