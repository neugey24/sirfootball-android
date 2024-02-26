package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.ChangeTeamNameFormData
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.NewTeamFormData
import com.sirfootball.android.structure.AvatarGroup
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
                Log.i("Save", "Success point in ViewModel reached - join league")
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during joining league with team data"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun changeTeamName(teamId: Int, nameData : ChangeTeamNameFormData) {
        viewModelScope.launch {
            try {
                val response = apiService.changeTeamName(teamId = teamId, postData = nameData)
                Log.i("Save", "Success point in ViewModel reached - change team name")
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during change team name"
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

    fun leaveLeague(teamId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.leaveLeague(teamId = teamId)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during leave league"
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

    fun performTierRankingSwap(teamId: Int, slot1: String, slot2: String) {
        viewModelScope.launch {
            try {
                val response = apiService.swapTierRankings(teamId = teamId, slot1 = slot1, slot2 = slot2)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during swap tier ranking on team"
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

    fun savePick(teamId: Int, homeTeamAbbrev: String, homeWin: String) {
        viewModelScope.launch {
            try {
                Log.i("save pickem", "saving it: $homeTeamAbbrev")
                val response = apiService.savePick(teamId = teamId, homeTeamAbbrev = homeTeamAbbrev,
                    homeWin = homeWin)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during save pick em"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun saveSpell(teamId: Int, spellIndex: Int) {
        viewModelScope.launch {
            try {
                Log.i("save spell", "saving it: $spellIndex")
                val response = apiService.saveSpell(teamId = teamId, spellIndex = spellIndex)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during save spells"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun saveDoubleDown(teamId: Int, playerId: Int, weekNum: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.saveDoubleDown(teamId = teamId, playerId = playerId, weekNum = weekNum)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during save double down"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun savePennant(teamId: Int, pennantKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.savePennant(teamId = teamId, pennantKey = pennantKey)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during save pennant"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

    fun changeAvatar(teamId: Int, groupKey: String, avatarName: String) {
        viewModelScope.launch {
            try {
                val response = apiService.changeAvatar(teamId, groupKey, avatarName)
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during change avatar"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }
}