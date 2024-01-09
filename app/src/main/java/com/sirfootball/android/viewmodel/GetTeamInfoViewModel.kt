package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.GetTeamInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTeamInfoViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getTeamInfoResponse = mutableStateOf<ApiState<GetTeamInfoResponse>>(ApiState.Loading)
    val getTeamInfoResponse : State<ApiState<GetTeamInfoResponse>> = _getTeamInfoResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Team id being loaded is ${teamId}")
                val response = apiService.getTeamInfo(teamId)
                _getTeamInfoResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get team info"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamInfoResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}