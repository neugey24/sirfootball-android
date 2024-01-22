package com.sirfootball.android.ui.root

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.R
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.Crown
import com.sirfootball.android.structure.SirGame
import com.sirfootball.android.viewmodel.GetUserCrownsViewModel


@Composable
fun UserCrownsPage() {
    val viewModel = hiltViewModel<GetUserCrownsViewModel>()
    val loadState = viewModel.getCrownsResponse.value

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }
    val padTop = Modifier.padding(top = 25.dp)

    Text(text = " Your Crowns", fontSize = 36.sp)

    Row(verticalAlignment = Alignment.Top, modifier = padTop) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
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
                        for (crown in responseData.crowns) {
                            item {
                                RenderCrown(crown)
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
    }

}

@Composable
fun RenderCrown(crown: Crown) {
    val crownImageId = LocalContext.current.resources.getIdentifier(
        "crown_${crown.image.replace(".jpeg", "")}", "drawable",
        LocalContext.current.packageName)

    Text(" ", fontSize = 5.sp)
    Divider()
    Text(" ", fontSize = 5.sp)
    Row(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.background(color = Color.White).fillMaxWidth().padding(
            horizontal = 8.dp,
            vertical = 8.dp,
        )) {
        Column {
            Image(
                painter = painterResource( id = crownImageId),
                contentDescription = "Game Type",
                modifier = Modifier.size(120.dp)
            )
        }
        Column {
            Text(crown.name, fontSize = 20.sp, color = colorResource(R.color.panel_fg))
            Text("Earned: ${crown.rewardDate}", fontSize = 12.sp, color = colorResource(R.color.panel_fg))
            Text("Earned: ${crown.detail}", fontSize = 14.sp, color = colorResource(R.color.panel_fg))
            Text("(${crown.awardNotes})", fontSize = 12.sp, color = colorResource(R.color.panel_fg))
        }
    }
}
