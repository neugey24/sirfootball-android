package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadUserCrownsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetUserCrownsViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getCrownsResponse = mutableStateOf<ApiState<LoadUserCrownsResponse>>(ApiState.Loading)
    val getCrownsResponse : State<ApiState<LoadUserCrownsResponse>> = _getCrownsResponse

    fun fetch() {
        viewModelScope.launch {
            try {
                Log.i("Load", "crowns being loaded for user")
                val crownsResponse = apiService.getCrowns()
                _getCrownsResponse.value = ApiState.Success(crownsResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get crowns"
                Log.e("API_ERROR", errorMessage, e)
                _getCrownsResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}