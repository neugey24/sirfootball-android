package com.sirfootball.android.ui.team.detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.model.RosterAction
import com.sirfootball.android.data.model.RosterSlotInfo
import com.sirfootball.android.data.model.SlotPlayer
import com.sirfootball.android.ui.nav.SFBottomNavItem
import com.sirfootball.android.viewmodel.DataPersistenceViewModel

@Composable
fun TeamRosterActionDetail(teamId: Int, forSlot : RosterSlotInfo,
                           sheetValueChange: (Boolean) -> Unit,
                           updateNeeded: (Boolean) -> Unit
                           ) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =
    Modifier
        .padding(horizontal = 6.dp)
        .height(640.dp).fillMaxWidth()

        ) {
        val teamAbbrevShow = if (forSlot.slotPlayer?.teamAbbrev == null) "FA" else forSlot.slotPlayer.teamAbbrev
        val teamShow = if (forSlot.slotPlayer?.position != "DFST") " " else teamAbbrevShow

        Text("Roster Actions for Slot: ${forSlot.slotName}", fontSize = 22.sp, textAlign = TextAlign.Center)
        Text("Current Player: ${forSlot.slotPlayer?.position} ${forSlot.slotPlayer?.fullName} $teamShow")
        Text(" ", fontSize = 16.sp)
        Divider()
        for (action in forSlot.availableActions) {
            RenderSlotAction(teamId, forSlot.slotPlayer, action, sheetValueChange, updateNeeded)
        }

        Text(" ", fontSize = 8.sp)

        Button(
            contentPadding = PaddingValues(all = 1.dp),
            modifier = Modifier.height(24.dp).width(180.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            onClick =  {  sheetValueChange(false) },
        ) {
            Text("Cancel Roster Action", fontSize = 14.sp )
        }

    }

}

@Composable
private fun RenderSlotAction(
    teamId: Int,
    slotPlayer: SlotPlayer?,
    action: RosterAction,
    sheetValueChange: (Boolean) -> Unit,
    updateNeededIn: (Boolean) -> Unit
) {
    val saveViewModel = hiltViewModel<DataPersistenceViewModel>()
    val saveState = saveViewModel.saveResponse.value
    val confirmDrop = remember { mutableStateOf(false) }
    val actionExecuted = remember { mutableStateOf(false) }

    if (action.type == "Swap") {
         Text(action.description, fontSize = 15.sp, fontWeight = FontWeight.Bold)

        Button(
            contentPadding = PaddingValues(all = 1.dp),
            modifier = Modifier.height(24.dp).width(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                contentColor = Color.White),
            onClick =  {
                actionExecuted.value = true
                saveViewModel.performRosterSwap(teamId, action.sourceSlot.toString(), action.destSlot.toString())
            },
        ) {
            Text("GO", fontSize = 14.sp )
        }

    } else {

            Text(action.description, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Button(
                contentPadding = PaddingValues(all = 1.dp),
                modifier = Modifier.height(24.dp).width(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue,
                    contentColor = Color.White),
                onClick =  {
                    if (confirmDrop.value) {
                        actionExecuted.value = true
                        saveViewModel.dropTeamPlayer(teamId, action.sourceSlot.toString())
                    }},
            ) {
                Text("GO", fontSize = 14.sp )
            }

            Row {
            Checkbox(
                checked = confirmDrop.value,
                onCheckedChange = { isChecked -> confirmDrop.value = isChecked },
                modifier = Modifier.size(15.dp)
            )
            Text("  Confirm Drop of ${slotPlayer?.position} ${slotPlayer?.fullName}")
        }
    }
    Divider()

    when (saveState) {
        is ApiState.Loading -> {
            Log.i("Save", "Loading the save state")
        }

        is ApiState.Success -> {
            Log.i("Save", "In the success block")
            if (actionExecuted.value) {
                Log.i("Save", "Roster update successful")
                updateNeededIn(true)
                sheetValueChange(false)
                actionExecuted.value = false
            }

        }

        else -> {
            Log.i("Save", "Roster update failed")
        }
    }
}



