package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamRosterCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTeamRosterViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getTeamRosterResponse = mutableStateOf<ApiState<LoadTeamRosterCompositeResponse>>(ApiState.Loading)
    val getTeamRosterResponse : State<ApiState<LoadTeamRosterCompositeResponse>> = _getTeamRosterResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Team roster being loaded for team id ${teamId}")
                val teamResponse = apiService.getTeamInfo(teamId)
                val rosterResponse = apiService.getTeamRoster(teamId)
                val compositeResponse = LoadTeamRosterCompositeResponse(roster = rosterResponse,
                    info = teamResponse)
                _getTeamRosterResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team roster"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamRosterResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}