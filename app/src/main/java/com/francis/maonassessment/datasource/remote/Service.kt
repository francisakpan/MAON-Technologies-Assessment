package com.francis.maonassessment.datasource.remote

import com.francis.maonassessment.data.model.response.CompetitionResponse
import com.francis.maonassessment.data.model.response.SquadResponse
import com.francis.maonassessment.data.model.response.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("/v2/competitions")
    suspend fun getCompetitions(): Response<CompetitionResponse>

    @GET("/v2/competitions/{id}/teams")
    suspend fun getTeamsInCompetition(@Path("id") id: Long): Response<TeamResponse>

    @GET("/v2/teams/{id}")
    suspend fun getTeamSquad(@Path("id") id: Long): Response<SquadResponse>
}