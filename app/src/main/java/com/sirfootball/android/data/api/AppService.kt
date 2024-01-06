package com.sirfootball.android.data.api

import com.sirfootball.android.data.model.LoadUserTeamsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface AppService {

    companion object {
        const val APP_SECRET = "e68b7621452fc41e7295cfc976d411a9"
        const val APP_USER_TOKEN = "ac15fa680b51f84de47cd0a6f81d1d31"
    }

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET",
        "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeams")
    suspend fun getTeams(): LoadUserTeamsResponse
}