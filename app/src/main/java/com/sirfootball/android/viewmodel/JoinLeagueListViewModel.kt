package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadJoinLeagueListResponse
import com.sirfootball.android.data.model.NewTeamFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JoinLeagueListViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _response = mutableStateOf<ApiState<LoadJoinLeagueListResponse>>(ApiState.Loading)
    val joinLeagueListResponse : State<ApiState<LoadJoinLeagueListResponse>> = _response

    fun fetch() {
        viewModelScope.launch {
            try {
                val response = apiService.joinLeagueList()
                _response.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during join league list"
                Log.e("API_ERROR", errorMessage, e)
                _response.value = ApiState.Error(errorMessage)
            }
        }
    }

}