package com.sirfootball.android.ui.add

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.AvailableLeague
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.NewTeamFormData
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.JoinLeagueListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddForGamePage(navController: NavHostController, gameAbbrev: String) {

    val viewModel = hiltViewModel<JoinLeagueListViewModel>()
    val loadState = viewModel.joinLeagueListResponse.value

    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value

    val sheetUp = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var toJoinLeague by remember { mutableStateOf(AvailableLeague()) }

    var teamCity by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("") }
    var teamAbbrev by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        viewModel.fetch()
    }
    val gameLogoId = LocalContext.current.resources.getIdentifier(
        "game_logo_${SirGame.GAME_DATA[gameAbbrev]?.get("logo")}", "drawable",
        LocalContext.current.packageName)
    Row(verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(top = 25.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Image(
            painter = painterResource( id = gameLogoId),
            contentDescription = "Game Type",
            modifier = Modifier.size(100.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
            Text(" ")
            Text(
                SirGame.GAME_DATA[gameAbbrev]?.get("name").toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Available Leagues", fontSize = 18.sp)
        }

    }

    Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)) {
        when (loadState) {
            is ApiState.Loading -> {
                Text("Loading Data ...")
            }

            is ApiState.Success -> {
                val responseData = loadState.data

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                                .size(570.dp)

                ) {
                    val leaguesForGame = responseData.openLeagues[gameAbbrev] ?: emptyList()
                    for (leagueInfo in leaguesForGame) {
                        item {
                            RenderLeagueBlock(leagueInfo, sheetValueChange = {showBottomSheet = it},
                                toJoinLeague = {toJoinLeague = it},
                                saveViewModel = saveViewModel, saveState = saveState,
                                navController = navController
                                )
                        }
                     }
                }
            }

            else -> {
                val error = loadState.toString()
                Text("Error loading teamsOccurred: $error")
            }
        }

    } // end column

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = colorResource(R.color.panel_bg),
            contentColor = colorResource(R.color.panel_fg),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetUp
        ) {
            // Sheet content
            Column(modifier = Modifier
                .height(810.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Join League", fontSize = 24.sp)
                Text(toJoinLeague.league_name, fontSize = 17.sp)
                Divider(modifier = Modifier.padding(4.dp))

                OutlinedTextField(
                    textStyle = TextStyle(color = colorResource(R.color.panel_fg)),
                    modifier = Modifier.padding(2.dp),
                    value = teamCity,
                    onValueChange = { teamCity = it },
                    label = { Text("City Name (3-12 characters):") }
                )

                OutlinedTextField(
                    textStyle = TextStyle(color = colorResource(R.color.panel_fg)),
                    modifier = Modifier.padding(4.dp),
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text("Team Name (3-12 characters):") }
                )

                OutlinedTextField(
                    textStyle = TextStyle(color = colorResource(R.color.panel_fg)),
                    modifier = Modifier.padding(4.dp),
                    value = teamAbbrev,
                    onValueChange = { teamAbbrev = it },
                    label = { Text("Team Abbrev. (2-4 letters max):") }
                )

                Row(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                ) {
                    ElevatedButton(contentPadding = PaddingValues(all = 3.dp),
                        modifier = Modifier.size(width = 60.dp, height = 26.dp),
                        onClick = {
                            Log.i("Save", "Perform League Join here")
                            val formData = NewTeamFormData(
                                cityName = teamCity, teamName = teamName, teamAbbrev = teamAbbrev)
                            saveViewModel.joinLeague(toJoinLeague.league_id, formData)
                        }
                    ) {
                        Text("Join", fontSize = 14.sp)
                        when (saveState) {
                            is ApiState.Loading -> {

                            }

                            is ApiState.Success -> {
                                Log.i("Save", "Join league successful response received")
                                navController.navigate(SFBottomNavItem.LockerRoom.route)
                            }

                            else -> {
                                Log.i("Save", "Join league failed")
                            }
                        }
                    }
                    Text("  ")
                    ElevatedButton(contentPadding = PaddingValues(all = 3.dp),
                        modifier = Modifier.size(width = 60.dp, height = 26.dp),
                        onClick = {
                            showBottomSheet = false
                        }
                    ) {
                        Text("Cancel", fontSize = 14.sp)
                    }
                }

                Text(" ")
            }

        }
    }

}

@Composable
fun RenderLeagueBlock(
    leagueInfo: AvailableLeague,
    sheetValueChange: (Boolean) -> Unit,
    toJoinLeague: (AvailableLeague) -> Unit,
    saveViewModel: DataPersistenceViewModel,
    saveState: ApiState<GeneralPersistenceResponse>,
    navController: NavHostController
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier
            .background(color = colorResource(R.color.panel_bg))
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp,
            )) {

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(leagueInfo.league_name, fontSize = 22.sp, color = colorResource(R.color.panel_fg))

                if (leagueInfo.joinType == "claim") {
                    ElevatedButton(contentPadding = PaddingValues(all = 3.dp),
                        modifier = Modifier.size(width = 90.dp, height = 26.dp),
                        onClick = {
                            saveViewModel.claimTeam(leagueInfo.league_id)
                        }
                    ) {
                        Text("Claim Team", fontSize = 14.sp)
                        when (saveState) {
                            is ApiState.Loading -> {

                            }

                            is ApiState.Success -> {
                                Log.i("Save", "Claim team successful response received")
                                navController.navigate(SFBottomNavItem.LockerRoom.route)
                            }

                            else -> {
                                Log.i("Save", "Claim team failed")
                            }
                        }
                    }
                } else {
                    Text(
                        "Draft Date: ${leagueInfo.draft_date}",
                        fontSize = 16.sp,
                        color = colorResource(R.color.panel_fg)
                    )
                    Row {
                        Text(
                            "Human Teams: (${leagueInfo.teamCount}/${leagueInfo.league_size})      ",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.panel_fg)
                        )
                        ElevatedButton(contentPadding = PaddingValues(all = 3.dp),
                            modifier = Modifier.size(width = 90.dp, height = 26.dp),
                            onClick = {
                                toJoinLeague(leagueInfo)
                                sheetValueChange(true)
                            }
                        ) {
                            Text("Join", fontSize = 14.sp)
                        }
                    }
                }


        }
    }
}
