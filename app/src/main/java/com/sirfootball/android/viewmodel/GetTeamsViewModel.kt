package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadUserTeamsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTeamsViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getTeamsResponse = mutableStateOf<ApiState<LoadUserTeamsResponse>>(ApiState.Loading)
    val getTeamsResponse : State<ApiState<LoadUserTeamsResponse>> = _getTeamsResponse

    fun fetch() {
        viewModelScope.launch {
            try {
                val response = apiService.getTeams(produceAuthHeaders(applicationContext = applicationContext))
                _getTeamsResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get teams"
                Log.e("API_ERROR", errorMessage, e)
                _getTeamsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}