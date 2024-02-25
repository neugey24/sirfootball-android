package com.sirfootball.android.data.api

import com.sirfootball.android.data.model.ChangeTeamNameFormData
import com.sirfootball.android.data.model.GeneralPersistenceResponse
import com.sirfootball.android.data.model.GetLeagueInfoResponse
import com.sirfootball.android.data.model.GetPlayerInfoResponse
import com.sirfootball.android.data.model.GetTeamInfoResponse
import com.sirfootball.android.data.model.LoadAddSlotGatherResponse
import com.sirfootball.android.data.model.LoadJoinLeagueListResponse
import com.sirfootball.android.data.model.LoadLeagueHighScoresResponse
import com.sirfootball.android.data.model.LoadLeagueScoreboardResponse
import com.sirfootball.android.data.model.LoadLeagueStandingsResponse
import com.sirfootball.android.data.model.LoadLeagueTransactionsResponse
import com.sirfootball.android.data.model.LoadPreviewResponse
import com.sirfootball.android.data.model.LoadScorecardResponse
import com.sirfootball.android.data.model.LoadTeamAddPlayerResponse
import com.sirfootball.android.data.model.LoadTeamDoubleDownResponse
import com.sirfootball.android.data.model.LoadTeamPennantsResponse
import com.sirfootball.android.data.model.LoadTeamPicksResponse
import com.sirfootball.android.data.model.LoadTeamRosterResponse
import com.sirfootball.android.data.model.LoadTeamScheduleResponse
import com.sirfootball.android.data.model.LoadTeamSpellsCompositeResponse
import com.sirfootball.android.data.model.LoadTeamSpellsResponse
import com.sirfootball.android.data.model.LoadTeamTransactionsResponse
import com.sirfootball.android.data.model.LoadTeamWeeklySpecialResponse
import com.sirfootball.android.data.model.LoadUserCrownsResponse
import com.sirfootball.android.data.model.LoadUserTeamsResponse
import com.sirfootball.android.data.model.NewTeamFormData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AppService {

    companion object {
        const val APP_SECRET = "e68b7621452fc41e7295cfc976d411a9"
        //const val APP_USER_TOKEN = "ac15fa680b51f84de47cd0a6f81d1d31" // Stan
        const val APP_USER_TOKEN = "811c10051732f76384a1ac80face89fc" // Kyle
        //const val APP_USER_TOKEN = "c376416ee1a3bd63f762174dec276a81" // Eric Cartman
    }

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeams")
    suspend fun getTeams(): LoadUserTeamsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getCrowns")
    suspend fun getCrowns(): LoadUserCrownsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("joinLeagueList")
    suspend fun joinLeagueList(): LoadJoinLeagueListResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeamInfo/{teamId}")
    suspend fun getTeamInfo(@Path("teamId") teamId : Int): GetTeamInfoResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeamTrans/{teamId}")
    suspend fun getTeamTrans(@Path("teamId") teamId : Int): LoadTeamTransactionsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getTeamSchedule/{teamId}")
    suspend fun getTeamSchedule(@Path("teamId") teamId : Int): LoadTeamScheduleResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("game/DD1/{teamId}")
    suspend fun getDD1Picks(@Path("teamId") teamId : Int): LoadTeamPicksResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("game/BC/{teamId}")
    suspend fun getSpells(@Path("teamId") teamId : Int): LoadTeamSpellsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("game/DD2/{teamId}")
    suspend fun getDoubleDown(@Path("teamId") teamId : Int): LoadTeamDoubleDownResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("game/PP/{teamId}")
    suspend fun getPennants(@Path("teamId") teamId : Int): LoadTeamPennantsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("game/WS/{teamId}")
    suspend fun getSpecial(@Path("teamId") teamId : Int): LoadTeamWeeklySpecialResponse

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
    @GET("getLeagueTrans/{leagueId}")
    suspend fun getLeagueTrans(@Path("leagueId") leagueId : Int): LoadLeagueTransactionsResponse


    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getLeagueStandings/{leagueId}")
    suspend fun getLeagueStandings(@Path("leagueId") leagueId : Int): LoadLeagueStandingsResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getLeagueHighScores/{leagueId}")
    suspend fun getLeagueHighScores(@Path("leagueId") leagueId : Int): LoadLeagueHighScoresResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("getLeagueScoreboard/{leagueId}")
    suspend fun getLeagueScoreboard(@Path("leagueId") leagueId : Int): LoadLeagueScoreboardResponse

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
    @GET("leaveLeague/{teamId}")
    suspend fun leaveLeague(@Path("teamId") teamId : Int): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @POST("changeTeamName/{teamId}")
    suspend fun changeTeamName(@Path("teamId") teamId : Int, @Body postData : ChangeTeamNameFormData): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @PUT("game/DD1/{teamId}/{homeTeamAbbrev}/{homeWin}")
    suspend fun savePick(@Path("teamId") teamId : Int, @Path("homeTeamAbbrev") homeTeamAbbrev : String,
                         @Path("homeWin") homeWin : String): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @PUT("game/BC/{teamId}/{spellIndex}")
    suspend fun saveSpell(@Path("teamId") teamId : Int, @Path("spellIndex") spellIndex : Int): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @PUT("game/DD2/{teamId}/{playerId}/{weekNum}")
    suspend fun saveDoubleDown(@Path("teamId") teamId : Int, @Path("playerId") playerId : Int,
                               @Path("weekNum") weekNum : Int): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @PUT("game/PP/{teamId}/{pennantKey}")
    suspend fun savePennant(@Path("teamId") teamId : Int, @Path("pennantKey") pennantKey : String): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("claimTeam/{leagueId}")
    suspend fun claimTeam(@Path("leagueId") leagueId : Int): GeneralPersistenceResponse

    @Headers(value = ["X-SF-APP-SECRET: $APP_SECRET", "X-SF-APP-USER-TOKEN: $APP_USER_TOKEN"])
    @GET("changeAvatar/{teamId}/{groupKey}/{avatarName}")
    suspend fun changeAvatar(@Path("teamId") teamId : Int,
                             @Path("groupKey") groupKey : String, @Path("avatarName") avatarName : String): GeneralPersistenceResponse

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