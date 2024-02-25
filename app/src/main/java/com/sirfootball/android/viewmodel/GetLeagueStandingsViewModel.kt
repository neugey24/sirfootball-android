package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadLeagueStandingsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetLeagueStandingsViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getLeagueStandingsResponse = mutableStateOf<ApiState<LoadLeagueStandingsResponse>>(ApiState.Loading)
    val getLeagueStandingsResponse : State<ApiState<LoadLeagueStandingsResponse>> = _getLeagueStandingsResponse

    fun fetch(leagueId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "League standings being loaded for $leagueId")
                val response = apiService.getLeagueStandings(leagueId)
                _getLeagueStandingsResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get league info"
                Log.e("API_ERROR", errorMessage, e)
                _getLeagueStandingsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}