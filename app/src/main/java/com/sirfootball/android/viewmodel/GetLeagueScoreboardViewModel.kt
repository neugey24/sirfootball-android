package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadLeagueScoreboardCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetLeagueScoreboardViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getLeagueScoreboardResponse = mutableStateOf<ApiState<LoadLeagueScoreboardCompositeResponse>>(ApiState.Loading)
    val getLeagueScoreboardResponse : State<ApiState<LoadLeagueScoreboardCompositeResponse>> = _getLeagueScoreboardResponse

    fun fetch(leagueId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "League scoreboard being loaded for $leagueId")
                val score = apiService.getLeagueScoreboard(leagueId, produceAuthHeaders(applicationContext = applicationContext))
                val info = apiService.getLeagueInfo(leagueId, produceAuthHeaders(applicationContext = applicationContext))
                val response = LoadLeagueScoreboardCompositeResponse(scoreboard = score, info = info)
                _getLeagueScoreboardResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get league scoreboard"
                Log.e("API_ERROR", errorMessage, e)
                _getLeagueScoreboardResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}