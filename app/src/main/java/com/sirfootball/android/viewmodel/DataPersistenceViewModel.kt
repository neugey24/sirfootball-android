package com.sirfootball.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.AuthenticationResponse
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.LoginFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataPersistenceViewModel @Inject constructor(private val apiService: AppService, private val applicationContext: Context): SFViewModel() {

    private val _response = mutableStateOf<ApiState<GeneralPersistenceResponse>>(ApiState.Loading)
    val saveResponse : State<ApiState<GeneralPersistenceResponse>> = _response
    private val _authResponse = mutableStateOf<ApiState<AuthenticationResponse>>(ApiState.Loading)
    val authResponse : State<ApiState<AuthenticationResponse>> = _authResponse


    fun authenticate(loginFormData : LoginFormData) {

        viewModelScope.launch {
            try {
                val response = apiService.authenticate(apiHeaders = produceApiHeaders(), postData = loginFormData)
                if (response.result == "ok") {
                    Log.i("Save", "Successful login detected")
                    _authResponse.value = ApiState.Success(response)
                } else {
                    Log.i("Save", "Invalid login detected")
                    _authResponse.value = ApiState.Error("Invalid Login")
                }

            } catch (e: Exception) {
                val errorMessage = "error during authentication attempt"
                Log.e("API_ERROR", errorMessage, e)
                _authResponse.value = ApiState.Error(errorMessage)
            }
        }
    }

}