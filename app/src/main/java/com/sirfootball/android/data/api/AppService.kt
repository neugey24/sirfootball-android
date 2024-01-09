package com.sirfootball.android.data.api

import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.GetLeagueInfoResponse
import com.sirfootball.android.data.model.GetTeamInfoResponse
import com.sirfootball.android.data.model.LoadJoinLeagueListResponse
import com.sirfootball.android.data.model.LoadUserTeamsResponse
import com.sirfootball.android.data.model.NewTeamFormData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AppService {

    companion object {
        const val APP_SECRET = "e68b7621452fc41e7295cfc976d411a9"
        //const val APP_USER_TOKEN = "ac15fa680b51f84de47cd0a6f81d1d31" // Stan
        const val APP_USER_TOKEN = "811c10051732f76384a1ac80face89fc" // Kyle
    }

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeams")
    suspend fun getTeams(): LoadUserTeamsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("joinLeagueList")
    suspend fun joinLeagueList(): LoadJoinLeagueListResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @POST("joinLeague/{leagueId}")
    suspend fun joinLeague(@Path("leagueId") leagueId : Int, @Body postData : NewTeamFormData): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeamInfo/{teamId}")
    suspend fun getTeamInfo(@Path("teamId") teamId : Int): GetTeamInfoResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getLeagueInfo/{leagueId}")
    suspend fun getLeagueInfo(@Path("leagueId") leagueId : Int): GetLeagueInfoResponse
}