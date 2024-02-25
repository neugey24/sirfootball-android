package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.GetPlayerInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetPlayerInfoViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getPlayerInfoResponse = mutableStateOf<ApiState<GetPlayerInfoResponse>>(ApiState.Loading)
    val getPlayerInfoResponse : State<ApiState<GetPlayerInfoResponse>> = _getPlayerInfoResponse

    fun fetch(playerId: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Player info being loaded for id ${playerId}")
                val response = apiService.getPlayerInfo(playerId)
                _getPlayerInfoResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get player info"
                Log.e("API_ERROR", errorMessage, e)
                _getPlayerInfoResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}