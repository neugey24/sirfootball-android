package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadPreviewResponse
import com.sirfootball.android.data.model.LoadScorecardResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetScorecardViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getScorecardResponse = mutableStateOf<ApiState<LoadScorecardResponse>>(ApiState.Loading)
    val getScorecardResponse : State<ApiState<LoadScorecardResponse>> = _getScorecardResponse

    fun fetch(leagueId: Int, weekNum: Int, matchupNum: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Scorecard is being loaded")
                val response = apiService.getScorecard(leagueId, weekNum, matchupNum, produceAuthHeaders(applicationContext = applicationContext))
                _getScorecardResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get team info"
                Log.e("API_ERROR", errorMessage, e)
                _getScorecardResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}