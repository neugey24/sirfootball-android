package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadLeagueTransactionsCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetLeagueTransactionsViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getLeagueTransactionsResponse = mutableStateOf<ApiState<LoadLeagueTransactionsCompositeResponse>>(ApiState.Loading)
    val getLeagueTransactionsResponse : State<ApiState<LoadLeagueTransactionsCompositeResponse>> = _getLeagueTransactionsResponse

    fun fetch(leagueId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "League transactions being loaded for league id $leagueId")
                val leagueResponse = apiService.getLeagueInfo(leagueId)
                val tranResponse = apiService.getLeagueTrans(leagueId)
                val compositeResponse = LoadLeagueTransactionsCompositeResponse(trans = tranResponse,
                    info = leagueResponse)
                _getLeagueTransactionsResponse.value = ApiState.Success(compositeResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get league transactions"
                Log.e("API_ERROR", errorMessage, e)
                _getLeagueTransactionsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}