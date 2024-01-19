package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamDoubleDownCompositeResponse
import com.sirfootball.android.data.model.LoadTeamSpellsCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamDoubleDownListViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getDoubleDownListResponse = mutableStateOf<ApiState<LoadTeamDoubleDownCompositeResponse>>(ApiState.Loading)
    val getDoubleDownListResponse : State<ApiState<LoadTeamDoubleDownCompositeResponse>> = _getDoubleDownListResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                val ddResponse = apiService.getDoubleDown(teamId)
                val infoResponse = apiService.getTeamInfo(teamId)
                val rosterResponse = apiService.getTeamRoster(teamId)
                val comboResponse = LoadTeamDoubleDownCompositeResponse(dd = ddResponse, info = infoResponse,
                    roster = rosterResponse)
                _getDoubleDownListResponse.value = ApiState.Success(comboResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team double down"
                Log.e("API_ERROR", errorMessage, e)
                _getDoubleDownListResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}