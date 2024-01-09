package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.GetLeagueInfoResponse
import com.sirfootball.android.data.model.GetTeamInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetLeagueInfoViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getLeagueInfoResponse = mutableStateOf<ApiState<GetLeagueInfoResponse>>(ApiState.Loading)
    val getLeagueInfoResponse : State<ApiState<GetLeagueInfoResponse>> = _getLeagueInfoResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Team id being loaded is ${teamId}")
                val response = apiService.getLeagueInfo(teamId)
                _getLeagueInfoResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get team info"
                Log.e("API_ERROR", errorMessage, e)
                _getLeagueInfoResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}