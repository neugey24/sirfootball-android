package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamAddPlayerCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTeamAddPlayerViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getTeamAddCompositeResponse = mutableStateOf<ApiState<LoadTeamAddPlayerCompositeResponse>>(ApiState.Loading)
    val getTeamAddCompositeResponse : State<ApiState<LoadTeamAddPlayerCompositeResponse>> = _getTeamAddCompositeResponse

    fun fetch(teamId: Int, slotName: String, isDr: String, requestPos: String) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Team roster being loaded for add list for team id ${teamId}")
                val teamResponse = apiService.getTeamInfo(teamId)
                val addResponse = apiService.teamAddPlayerData(teamId, slotName, isDr, requestPos)
                val compositeResponse = LoadTeamAddPlayerCompositeResponse(add = addResponse,
                    info = teamResponse)
                _getTeamAddCompositeResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team add"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamAddCompositeResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}