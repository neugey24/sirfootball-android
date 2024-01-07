package com.sirfootball.android.ui.root

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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.AvailableLeague
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.ui.nav.AddRoutes
import com.sirfootball.android.viewmodel.JoinLeagueListViewModel


@Composable
fun AddTeamPage(navController: NavHostController) {
    val viewModel = hiltViewModel<JoinLeagueListViewModel>()
    val loadState = viewModel.joinLeagueListResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }
    val padTop = Modifier.padding(top = 25.dp)
    Row(verticalAlignment = Alignment.Top, modifier = padTop) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_black_24dp),
            contentDescription = "Add A Team",
            modifier = Modifier.size(46.dp)
        )
        Text(text = " Add A Team", fontSize = 36.sp)
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

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                .padding( start = 8.dp, end = 8.dp)
                                    .size(570.dp)

                    ) {
                        // responseData.openLeagues
                        for (gameType in SirGame.GAME_DATA.keys) {
                            item {
                                RenderGameBlock(gameType, responseData.openLeagues, navController)
                            }
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
fun RenderGameBlock(gameType: String, leagueInfo : Map<String, List<AvailableLeague>>, navController: NavHostController) {

    val gameLogoId = LocalContext.current.resources.getIdentifier(
        "game_logo_${SirGame.GAME_DATA[gameType]?.get("logo")}", "drawable",
        LocalContext.current.packageName)

    val leagueCount = if (leagueInfo.containsKey(gameType)) leagueInfo[gameType]?.size else 0

    Row(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.background(color = colorResource(R.color.panel_bg)).fillMaxWidth().padding(
            horizontal = 8.dp,
            vertical = 8.dp,
        )) {

            Image(
                painter = painterResource( id = gameLogoId),
                contentDescription = "Game Type",
                modifier = Modifier.size(100.dp)
            )
            Column {
                Text(
                    SirGame.GAME_DATA[gameType]?.get("name").toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.panel_fg)
                )
                if (leagueCount != null && leagueCount > 0) {
                    Text(
                        "Leagues: $leagueCount",
                        fontSize = 15.sp,
                        color = colorResource(R.color.panel_fg)
                    )
                    ElevatedButton(contentPadding = PaddingValues(all = 5.dp), modifier = Modifier.size(width = 190.dp, height = 32.dp), onClick = {
                        navController.navigate(
                            AddRoutes.LEAGUES_BY_GAME.replace(AddRoutes.ARG_TAG_GAME_ABBREV, gameType))
                    }
                    ) {
                        Text( "View Leagues", fontSize = 16.sp,)
                    }

                } else {
                    Text(
                        "No Leagues Available",
                        fontSize = 15.sp,
                        color = colorResource(R.color.panel_fg)
                    )
                }

            }
        }

}