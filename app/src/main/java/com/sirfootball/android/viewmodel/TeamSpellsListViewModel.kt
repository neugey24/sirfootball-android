package com.sirfootball.android.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirfootball.android.data.api.ApiState
import com.sirfootball.android.data.api.AppService
import com.sirfootball.android.data.model.LoadTeamSpellsCompositeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamSpellsListViewModel @Inject constructor(private val apiService: AppService): ViewModel() {

    private val _getSpellsListResponse = mutableStateOf<ApiState<LoadTeamSpellsCompositeResponse>>(ApiState.Loading)
    val getSpellsListResponse : State<ApiState<LoadTeamSpellsCompositeResponse>> = _getSpellsListResponse

    fun fetch(teamId: Int) {
        viewModelScope.launch {
            try {
                val spellsResponse = apiService.getSpells(teamId)
                val infoResponse = apiService.getTeamInfo(teamId)
                val comboResponse = LoadTeamSpellsCompositeResponse(spells = spellsResponse, info = infoResponse)
                _getSpellsListResponse.value = ApiState.Success(comboResponse)
            } catch (e: Exception) {
                val errorMessage = "error during get team picks"
                Log.e("API_ERROR", errorMessage, e)
                _getSpellsListResponse.value = ApiState.Error(errorMessage)
            }
        }
    }
}