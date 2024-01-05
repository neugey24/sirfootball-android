package com.sirfootball.android.client

import com.sirfootball.android.model.LoadUserTeamsResponse
import com.sirfootball.android.model.XTest3Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface AppApi {

    companion object {
        const val APP_SECRET = "e68b7621452fc41e7295cfc976d411a9"
        const val APP_USER_TOKEN = "ac15fa680b51f84de47cd0a6f81d1d31"
    }


//    @GET("xtest3")
//    suspend fun xtest3(): XTest3Response

    @GET("xtest3")
    fun xtest3(): Call<XTest3Response>

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET",
        "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeams")
    fun getTeams(): Call<LoadUserTeamsResponse>
}