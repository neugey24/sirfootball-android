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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.PennantInfo
import com.sirfootball.android.structure.SirAvatar
import com.sirfootball.android.viewmodel.DataPersistenceViewModel
import com.sirfootball.android.viewmodel.TeamPennantsListViewModel

@Composable
fun TeamPennantsPage(teamId : Int) {
    val viewModel = hiltViewModel<TeamPennantsListViewModel>()
    val loadState = viewModel.getPennantsResponse.value

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
                val team = responseData.info
                val ppInfo = responseData.pp

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
                Text("Pennant Play", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Pennant Selection - NFL Week ${ppInfo.weekNum}", fontSize = 22.sp)
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

                    for (entry in ppInfo.pennants) {
                        if (ppInfo.pennantData.containsKey(entry.key)) {
                            item {
                                RenderPennant( entry.key, entry.value, ppInfo.weekCommenced,
                                    saveViewModel, teamId, ppInfo.pennantData)
                                Divider()
                            }
                        }

                    }

                }
                Spacer(Modifier.weight(2f))
            }


            else -> {
                val error = loadState.toString()
                Text("Error loading team Occurred: $error")
            }
        }
    }

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state - save pennant selection")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block - save pennant selection")
            viewModel.fetch(teamId)
        }

        else -> {
            Log.i("Save", "Roster update failed - save pennant selection")
        }
    }

}

@Composable
fun RenderPennant(
    pennantKey: String,
    pennant: PennantInfo,
    weekCommenced: Boolean,
    saveViewModel: DataPersistenceViewModel,
    teamId: Int,
    pennantData: Map<String, String>
) {

    Row {
        Spacer(Modifier.weight(1f))

            if (pennantData[pennantKey] == "Used") {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock_black_24dp),
                    contentDescription = "Used Pennant",
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(24.dp)
                )
            } else {
                if (pennantData[pennantKey] == "Current") {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle_star_black_24dp),
                        contentDescription = "Current Pennant",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(24.dp)
                    )
                } else {
                    if (weekCommenced) {
                        Text("-", color = Color.Gray, fontSize = 15.sp, modifier = Modifier.width(24.dp))
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_circle_blank_black_24dp),
                            contentDescription = "Unselected Pennant",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(onClick = {
                                    saveViewModel.savePennant(
                                        teamId = teamId, pennantKey = pennantKey )
                                })
                        )
                    }
                }

            }
        Spacer(Modifier.weight(1f))
        Column {
            Text("${pennant.name} - Type - ${pennant.type}", fontWeight = FontWeight.Bold,
                color = colorResource(R.color.panel_fg), fontSize = 16.sp, modifier = Modifier.width(340.dp))
            Text("${pennant.detail}",
                color = colorResource(R.color.panel_fg), fontSize = 13.sp, modifier = Modifier.width(340.dp))
        }
        Spacer(Modifier.weight(1f))

    }

}






