package com.sirfootball.android.data.api

sealed class ApiState<out R> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val errorMessage: String) : ApiState<Nothing>()
    data object Loading : ApiState<Nothing>()
}