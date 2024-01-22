package com.sirfootball.android.ui.team.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.GetTeamInfoViewModel

@Composable
fun TeamChangeAvatarSelectAvatarPage(navController: NavHostController, teamId : Int, avatarGroup: String) {
    val viewModel = hiltViewModel<GetTeamInfoViewModel>()
    val loadState = viewModel.getTeamInfoResponse.value

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value

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
                Text("Group: $avatarGroup", fontSize = 22.sp)
                Text("(Tap avatar to change to it)", fontSize = 14.sp)
                Spacer(Modifier.weight(1f))

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color =
                        colorResource(R.color.panel_bg)
                    )
                    .size(410.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                    val avatarList = SirAvatar.getAvatars(avatarGroup = avatarGroup)
                    for (avatar in avatarList) {
                        val currentKey = "$avatarGroup:$avatar"
                        item {
                            Text(" ", fontSize = 12.sp)
                            Image(
                                painter = painterResource(
                                    id = SirAvatar.getResourceIdForAvatar(
                                        currentKey,
                                        team.team_num
                                    )
                                ),
                                contentDescription = "Team Avatar",
                                modifier = Modifier.size(160.dp).clickable(onClick = {
                                    saveViewModel.changeAvatar(teamId, avatarGroup, avatar)
                                })
                            )
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

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state - change avatar")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - change avatar")
            navController.navigate(SFBottomNavItem.LockerRoom.route)
        }

        else -> {
            Log.i("Save", "Roster update failed - change avatar")
        }
    }

}







