package com.sirfootball.android.ui.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirfootball.android.R

@Composable
fun StubPage() {

    Divider(modifier = Modifier.padding(top = 22.dp, bottom = 8.dp))
    Column(
        modifier = Modifier
            .background(Color.Blue)
            .size(500.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(1) {
            Text("Team Settings $it", modifier = Modifier.padding(2.dp), fontSize = 22.sp, color = Color.White)
        }
    }

}