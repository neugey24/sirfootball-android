package com.sirfootball.android.ui.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sirfootball.android.R

@Composable
fun StubPage(titleIn: String) {

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        Text(titleIn)
        val imageModifier = Modifier.size(120.dp)
           // .background(Color.Yellow)

        Image(
            painter = painterResource(id = R.drawable.dd1),
            contentDescription = "Decisions Decisions",
            modifier = imageModifier
        )
        Image(
            painter = painterResource(id = R.drawable.game_title_decdec),
            contentDescription = "Decisions Decisions Title",
            modifier = imageModifier
        )
    }

}