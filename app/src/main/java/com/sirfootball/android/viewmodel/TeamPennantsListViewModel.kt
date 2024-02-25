package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamPennantsCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamPennantsListViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getPennantsResponse = mutableStateOf<ApiState<LoadTeamPennantsCompositeResponse>>(ApiState.Loading)
    val getPennantsResponse : State<ApiState<LoadTeamPennantsCompositeResponse>> = _getPennantsResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                val pennantsResponse = apiService.getPennants(teamId)
                val infoResponse = apiService.getTeamInfo(teamId)
                val comboResponse = LoadTeamPennantsCompositeResponse(pp = pennantsResponse, info = infoResponse)
                _getPennantsResponse.value = ApiState.Success(comboResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team pennants"
                Log.e("API_ERROR", errorMessage, e)
                _getPennantsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}