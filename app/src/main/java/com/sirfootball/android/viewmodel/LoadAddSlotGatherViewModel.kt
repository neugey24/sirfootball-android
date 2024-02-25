package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadAddSlotPlayerGatherCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoadAddSlotGatherViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getTeamAddCompositeResponse = mutableStateOf<ApiState<LoadAddSlotPlayerGatherCompositeResponse>>(ApiState.Loading)
    val getTeamAddCompositeResponse : State<ApiState<LoadAddSlotPlayerGatherCompositeResponse>> = _getTeamAddCompositeResponse

    fun fetch(teamId: Int, playerId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Add Slot Gather for team id $teamId and player id $playerId")
                val teamResponse = apiService.getTeamInfo(teamId)
                val addResponse = apiService.addSelectSlotGather(teamId, playerId)
                val compositeResponse = LoadAddSlotPlayerGatherCompositeResponse(add = addResponse,
                    info = teamResponse)
                _getTeamAddCompositeResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team add - slot gather"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamAddCompositeResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}