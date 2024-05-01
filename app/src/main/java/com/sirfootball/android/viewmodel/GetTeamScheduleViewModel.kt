package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamScheduleCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTeamScheduleViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getTeamScheduleResponse = mutableStateOf<ApiState<LoadTeamScheduleCompositeResponse>>(ApiState.Loading)
    val getTeamScheduleResponse : State<ApiState<LoadTeamScheduleCompositeResponse>> = _getTeamScheduleResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Team schedule being loaded for team id $teamId")
                val teamResponse = apiService.getTeamInfo(teamId, produceAuthHeaders(applicationContext = applicationContext))
                val scheduleResponse = apiService.getTeamSchedule(teamId, produceAuthHeaders(applicationContext = applicationContext))
                val compositeResponse = LoadTeamScheduleCompositeResponse(schedule = scheduleResponse,
                    info = teamResponse)
                _getTeamScheduleResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team schedule"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamScheduleResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}