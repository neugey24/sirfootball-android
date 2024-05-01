package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadPreviewResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetPreviewViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _getPreviewResponse = mutableStateOf<ApiState<LoadPreviewResponse>>(ApiState.Loading)
    val getPreviewResponse : State<ApiState<LoadPreviewResponse>> = _getPreviewResponse

    fun fetch(leagueId: Int, weekNum: Int, matchupNum: Int) {
        viewModelScope.launch {
            try {
                Log.i("Load", "Preview is being loaded")
                val response = apiService.getPreview(leagueId, weekNum, matchupNum, produceAuthHeaders(applicationContext = applicationContext))
                _getPreviewResponse.value = ApiState.Success(response)
            } catch (e: Exception) {
                val errorMessage = "error during get team info"
                Log.e("API_ERROR", errorMessage, e)
                _getPreviewResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}