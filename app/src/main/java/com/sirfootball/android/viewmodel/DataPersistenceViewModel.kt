package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.NewTeamFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataPersistenceViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _response = mutableStateOf<ApiState<GeneralPersistenceResponse>>(ApiState.Loading)
    val saveResponse : State<ApiState<GeneralPersistenceResponse>> = _response

    fun joinLeague(leagueId: Int, newTeamData : NewTeamFormData) {
        viewModelScope.launch {
            try {
                Log.i("Save", "For Save In viewmodel leagueId is $leagueId")
                Log.i("Save", "For Save In viewmodel cityName is ${newTeamData.cityName}")
                val response = apiService.joinLeague(leagueId = leagueId, postData = newTeamData)
                Log.i("Save", "Success point in ViewModel reached")
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during joining league with team data"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun claimTeam(leagueId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.claimTeam(leagueId = leagueId)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during claiming team with team data"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun performRosterSwap(teamId: Int, slot1: String, slot2: String) {
        viewModelScope.launch {
            try {
                val response = apiService.performSlotSwap(teamId = teamId, slot1 = slot1, slot2 = slot2)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during swap slot on team"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun dropTeamPlayer(teamId: Int, dropSlot: String) {
        viewModelScope.launch {
            try {
                val response = apiService.dropTeamPlayer(teamId = teamId, dropSlot = dropSlot)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during drop of player from team"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun teamAddPerformExecute(teamId: Int, playerId: Int, slotName: String, onWaivers: String) {
        viewModelScope.launch {
            try {
                val response = apiService.teamAddPerformExecute(teamId = teamId, playerId = playerId,
                    slotName = slotName, onWaivers = onWaivers)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during perform add of player"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }
}