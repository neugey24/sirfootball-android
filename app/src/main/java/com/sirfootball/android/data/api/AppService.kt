package com.sirfootball.android.data.api

import com.sirfootball.android.data.model.AuthenticationResponse
import com.sirfootball.android.data.model.GetLeagueInfoResponse
import com.sirfootball.android.data.model.GetTeamInfoResponse
import com.sirfootball.android.data.model.LoadLeagueHighScoresResponse
import com.sirfootball.android.data.model.LoadLeagueScoreboardResponse
import com.sirfootball.android.data.model.LoadLeagueStandingsResponse
import com.sirfootball.android.data.model.LoadLeagueTransactionsResponse
import com.sirfootball.android.data.model.LoadPreviewResponse
import com.sirfootball.android.data.model.LoadScorecardResponse
import com.sirfootball.android.data.model.LoadTeamScheduleResponse
import com.sirfootball.android.data.model.LoadTeamTransactionsResponse
import com.sirfootball.android.data.model.LoadUserCrownsResponse
import com.sirfootball.android.data.model.LoadUserTeamsResponse
import com.sirfootball.android.data.model.LoginFormData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface AppService {


    @GET("user/getTeams")
    suspend fun getTeams(@HeaderMap apiHeaders : Map<String, String>): LoadUserTeamsResponse

    @GET("user/getCrowns")
    suspend fun getCrowns(@HeaderMap apiHeaders : Map<String, String>): LoadUserCrownsResponse

    @GET("user/getTeamInfo/{teamId}")
    suspend fun getTeamInfo(@Path("teamId") teamId : Int, @HeaderMap apiHeaders : Map<String, String>): GetTeamInfoResponse

    @GET("user/getTeamTrans/{teamId}")
    suspend fun getTeamTrans(@Path("teamId") teamId : Int, @HeaderMap apiHeaders : Map<String, String>): LoadTeamTransactionsResponse

    @GET("user/getTeamSchedule/{teamId}")
    suspend fun getTeamSchedule(@Path("teamId") teamId : Int, @HeaderMap apiHeaders : Map<String, String>): LoadTeamScheduleResponse

    @GET("user/getLeagueInfo/{leagueId}")
    suspend fun getLeagueInfo(@Path("leagueId") leagueId : Int, @HeaderMap apiHeaders : Map<String, String>): GetLeagueInfoResponse

    @GET("user/getLeagueTrans/{leagueId}")
    suspend fun getLeagueTrans(@Path("leagueId") leagueId : Int, @HeaderMap apiHeaders : Map<String, String>): LoadLeagueTransactionsResponse


    @GET("user/getLeagueStandings/{leagueId}")
    suspend fun getLeagueStandings(@Path("leagueId") leagueId : Int, @HeaderMap apiHeaders : Map<String, String>): LoadLeagueStandingsResponse

    @GET("user/getLeagueHighScores/{leagueId}")
    suspend fun getLeagueHighScores(@Path("leagueId") leagueId : Int, @HeaderMap apiHeaders : Map<String, String>): LoadLeagueHighScoresResponse

    @GET("user/etLeagueScoreboard/{leagueId}")
    suspend fun getLeagueScoreboard(@Path("leagueId") leagueId : Int, @HeaderMap apiHeaders : Map<String, String>): LoadLeagueScoreboardResponse

    @GET("user/getPreview/{leagueId}/{weekNum}/{matchupNum}")
    suspend fun getPreview(@Path("leagueId") leagueId : Int,
                           @Path("weekNum") weekNum : Int,
                           @Path("matchupNum") matchupNum : Int, @HeaderMap apiHeaders : Map<String, String>): LoadPreviewResponse

    @GET("user/getScorecard/{leagueId}/{weekNum}/{matchupNum}")
    suspend fun getScorecard(@Path("leagueId") leagueId : Int,
                           @Path("weekNum") weekNum : Int,
                           @Path("matchupNum") matchupNum : Int, @HeaderMap apiHeaders : Map<String, String>): LoadScorecardResponse

    @POST("authenticate")
    suspend fun authenticate(@HeaderMap apiHeaders : Map<String, String>, @Body postData : LoginFormData): AuthenticationResponse




}