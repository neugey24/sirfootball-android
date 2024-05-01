package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadLeagueHighScoresCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetLeagueHighScoresViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getLeagueHighScoresResponse = mutableStateOf<ApiState<LoadLeagueHighScoresCompositeResponse>>(ApiState.Loading)
    val getLeagueHighScoresResponse : State<ApiState<LoadLeagueHighScoresCompositeResponse>> = _getLeagueHighScoresResponse

    fun fetch(leagueId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "League high scores being loaded for league id $leagueId")
                val leagueResponse = apiService.getLeagueInfo(leagueId, produceAuthHeaders(applicationContext = applicationContext))
                val scoresResponse = apiService.getLeagueHighScores(leagueId, produceAuthHeaders(applicationContext = applicationContext))
                val compositeResponse = LoadLeagueHighScoresCompositeResponse(high = scoresResponse,
                    info = leagueResponse)
                _getLeagueHighScoresResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get league high scores"
                Log.e("API_ERROR", errorMessage, e)
                _getLeagueHighScoresResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}