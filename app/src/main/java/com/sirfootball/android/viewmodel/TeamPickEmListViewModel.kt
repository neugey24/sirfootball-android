package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamPicksCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamPickEmListViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getPicksListResponse = mutableStateOf<ApiState<LoadTeamPicksCompositeResponse>>(ApiState.Loading)
    val getPicksListResponse : State<ApiState<LoadTeamPicksCompositeResponse>> = _getPicksListResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                val picksResponse = apiService.getDD1Picks(teamId)
                val infoResponse = apiService.getTeamInfo(teamId)
                val comboResponse = LoadTeamPicksCompositeResponse(picks = picksResponse, info = infoResponse)
                _getPicksListResponse.value = ApiState.Success(comboResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team picks"
                Log.e("API_ERROR", errorMessage, e)
                _getPicksListResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}