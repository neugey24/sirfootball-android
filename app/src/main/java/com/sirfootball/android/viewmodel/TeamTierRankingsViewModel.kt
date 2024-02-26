package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamTierRankingsCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamTierRankingsViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getTierRankingsResponse = mutableStateOf<ApiState<LoadTeamTierRankingsCompositeResponse>>(ApiState.Loading)
    val getTierRankingsResponse : State<ApiState<LoadTeamTierRankingsCompositeResponse>> = _getTierRankingsResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                val tierResponse = apiService.getTierRankings(teamId)
                val infoResponse = apiService.getTeamInfo(teamId)
                val rosterResponse = apiService.getTeamRoster(teamId)
                val comboResponse = LoadTeamTierRankingsCompositeResponse(tier = tierResponse,
                    info = infoResponse, roster = rosterResponse)
                _getTierRankingsResponse.value = ApiState.Success(comboResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team tier rankings"
                Log.e("API_ERROR", errorMessage, e)
                _getTierRankingsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}