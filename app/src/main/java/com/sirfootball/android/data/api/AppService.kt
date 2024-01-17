package com.sirfootball.android.data.api

import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.GetLeagueInfoResponse
import com.sirfootball.android.data.model.GetPlayerInfoResponse
import com.sirfootball.android.data.model.GetTeamInfoResponse
import com.sirfootball.android.data.model.LoadAddSlotGatherResponse
import com.sirfootball.android.data.model.LoadJoinLeagueListResponse
import com.sirfootball.android.data.model.LoadPreviewResponse
import com.sirfootball.android.data.model.LoadScorecardResponse
import com.sirfootball.android.data.model.LoadTeamAddPlayerResponse
import com.sirfootball.android.data.model.LoadTeamRosterResponse
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
        const val APP_USER_TOKEN = "ac15fa680b51f84de47cd0a6f81d1d31" // Stan
        //const val APP_USER_TOKEN = "811c10051732f76384a1ac80face89fc" // Kyle
        //const val APP_USER_TOKEN = "c376416ee1a3bd63f762174dec276a81" // Eric Cartman
    }

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeams")
    suspend fun getTeams(): LoadUserTeamsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("joinLeagueList")
    suspend fun joinLeagueList(): LoadJoinLeagueListResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeamInfo/{teamId}")
    suspend fun getTeamInfo(@Path("teamId") teamId : Int): GetTeamInfoResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getPlayerInfo/{playerId}")
    suspend fun getPlayerInfo(@Path("playerId") playerId : Int): GetPlayerInfoResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeamRoster/{teamId}")
    suspend fun getTeamRoster(@Path("teamId") teamId : Int): LoadTeamRosterResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getLeagueInfo/{leagueId}")
    suspend fun getLeagueInfo(@Path("leagueId") leagueId : Int): GetLeagueInfoResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getPreview/{leagueId}/{weekNum}/{matchupNum}")
    suspend fun getPreview(@Path("leagueId") leagueId : Int,
                           @Path("weekNum") weekNum : Int,
                           @Path("matchupNum") matchupNum : Int): LoadPreviewResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getScorecard/{leagueId}/{weekNum}/{matchupNum}")
    suspend fun getScorecard(@Path("leagueId") leagueId : Int,
                           @Path("weekNum") weekNum : Int,
                           @Path("matchupNum") matchupNum : Int): LoadScorecardResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("teamAddPlayerData/{teamId}/{slotName}/{isDr}/{requestPos}")
    suspend fun teamAddPlayerData(@Path("teamId") teamId : Int,
                             @Path("slotName") slotName : String,
                              @Path("isDr") isDr : String,
                              @Path("requestPos") requestPos : String): LoadTeamAddPlayerResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("addSelectSlotGather/{teamId}/{playerId}")
    suspend fun addSelectSlotGather(@Path("teamId") teamId : Int,
                                  @Path("playerId") playerId : Int): LoadAddSlotGatherResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @POST("joinLeague/{leagueId}")
    suspend fun joinLeague(@Path("leagueId") leagueId : Int, @Body postData : NewTeamFormData): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("claimTeam/{leagueId}")
    suspend fun claimTeam(@Path("leagueId") leagueId : Int): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("performSlotSwap/{teamId}/{slot1}/{slot2}")
    suspend fun performSlotSwap(@Path("teamId") teamId : Int,
                                @Path("slot1") slot1 : String,
                                @Path("slot2") slot2 : String): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("dropTeamPlayer/{teamId}/{dropSlot}")
    suspend fun dropTeamPlayer(@Path("teamId") teamId : Int,
                                @Path("dropSlot") dropSlot : String): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("teamAddPerformExecute/{teamId}/{playerId}/{slotName}/{onWaivers}")
    suspend fun teamAddPerformExecute(@Path("teamId") teamId : Int,
                                      @Path("playerId") playerId : Int,
                                @Path("slotName") slotName : String,
                                @Path("onWaivers") onWaivers : String): GeneralPersistenceResponse



}