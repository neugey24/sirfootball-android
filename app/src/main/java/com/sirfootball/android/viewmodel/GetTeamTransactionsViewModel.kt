package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamTransactionsCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTeamTransactionsViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getTeamTransactionsResponse = mutableStateOf<ApiState<LoadTeamTransactionsCompositeResponse>>(ApiState.Loading)
    val getTeamTransactionsResponse : State<ApiState<LoadTeamTransactionsCompositeResponse>> = _getTeamTransactionsResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Team transactions being loaded for team id $teamId")
                val teamResponse = apiService.getTeamInfo(teamId, produceAuthHeaders(applicationContext = applicationContext))
                val tranResponse = apiService.getTeamTrans(teamId, produceAuthHeaders(applicationContext = applicationContext))
                val compositeResponse = LoadTeamTransactionsCompositeResponse(tran = tranResponse,
                    info = teamResponse)
                _getTeamTransactionsResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team transactions"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamTransactionsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}