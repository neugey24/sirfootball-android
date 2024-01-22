package com.sirfootball.android.ui.team

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.ChangeTeamNameFormData
import com.sirfootball.android.data.model.NewTeamFormData
import com.sirfootball.android.data.model.TransactionInfo
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.ui.nav.TeamRoutes
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.GetTeamInfoViewModel
import com.sirfootball.android.viewmodel.GetTeamTransactionsViewModel


@Composable
fun TeamSettingsPage(navController: NavHostController, teamId : Int) {
    val viewModel = hiltViewModel<GetTeamInfoViewModel>()
    val loadState = viewModel.getTeamInfoResponse.value

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value

    var teamCity by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("") }
    var teamAbbrev by remember { mutableStateOf("") }
    val confirmLeave = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetch(teamId)
    }

    when (loadState) {
        is ApiState.Loading -> {
            Text("Loading Data ...")
        }

        is ApiState.Success -> {
            val team = loadState.data

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
                    text = "Change Team Name", fontSize = 28.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.padding(2.dp),
                    value = teamCity,
                    onValueChange = {
                        Log.i("name chg", "I changed name")
                        teamCity = it
                        },
                    label = { Text("City Name (3-12 characters):") }
                )

                OutlinedTextField(
                    modifier = Modifier.padding(4.dp),
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text("Team Name (3-12 characters):") }
                )

                OutlinedTextField(
                    modifier = Modifier.padding(4.dp),
                    value = teamAbbrev,
                    onValueChange = { teamAbbrev = it },
                    label = { Text("Team Abbrev. (2-4 letters max):") }
                )
                Text(" ", fontSize = 6.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 220.dp, height = 34.dp),
                    onClick = {
                        Log.i("Save", "Change team name here")
                        val formData = ChangeTeamNameFormData(
                            newCity = teamCity, newName = teamName, newAbbrev = teamAbbrev)
                        saveViewModel.changeTeamName(team.teamInfo.team_id, formData)
                    }
                ) {
                    Text("Save Changes", fontSize = 16.sp)

                }
                Text(" ", fontSize = 8.sp)
                Divider()
                Text(" ", fontSize = 14.sp)
                Text(
                    text = "Leave this League", fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row {
                    Checkbox(
                        checked = confirmLeave.value,
                        onCheckedChange = { isChecked -> confirmLeave.value = isChecked },
                        modifier = Modifier.size(15.dp)
                    )
                    Text("   Leave league and abandon team")
                }
                Text(" ", fontSize=8.sp)
                Button(contentPadding = PaddingValues(all = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                        contentColor = Color.White),
                    modifier = Modifier.size(width = 220.dp, height = 34.dp),
                    onClick = {
                        if (confirmLeave.value) {
                            Log.i("Save", "Leave League here")
                            saveViewModel.leaveLeague(team.teamInfo.team_id)
                        }
                    }
                ) {
                    Text("Leave", fontSize = 16.sp)
                }

            }

        }

        else -> {
            val error = loadState.toString()
            Text("Error loading team Occurred: $error")
        }

    }

    when (saveState) {
        is ApiState.Loading -> {

        }

        is ApiState.Success -> {
            Log.i("Save", "Change name successful response received")
            navController.navigate(SFBottomNavItem.LockerRoom.route)
        }

        else -> {
            Log.i("Save", "Change name of team failed")
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

