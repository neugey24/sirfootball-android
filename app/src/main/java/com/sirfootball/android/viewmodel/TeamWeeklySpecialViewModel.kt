package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamWeeklySpecialCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamWeeklySpecialViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getWeeklySpecialResponse = mutableStateOf<ApiState<LoadTeamWeeklySpecialCompositeResponse>>(ApiState.Loading)
    val getWeeklySpecialResponse : State<ApiState<LoadTeamWeeklySpecialCompositeResponse>> = _getWeeklySpecialResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                val specialResponse = apiService.getSpecial(teamId)
                val infoResponse = apiService.getTeamInfo(teamId)
                val comboResponse = LoadTeamWeeklySpecialCompositeResponse(special = specialResponse,
                    info = infoResponse)
                _getWeeklySpecialResponse.value = ApiState.Success(comboResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team picks"
                Log.e("API_ERROR", errorMessage, e)
                _getWeeklySpecialResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}