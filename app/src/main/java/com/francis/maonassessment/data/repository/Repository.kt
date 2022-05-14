package com.francis.maonassessment.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.francis.maonassessment.data.model.response.CompetitionResponse
import com.francis.maonassessment.data.model.response.SquadResponse
import com.francis.maonassessment.data.model.response.TeamResponse
import com.francis.maonassessment.data.model.table.CompetitionEntity
import com.francis.maonassessment.data.model.table.PlayerEntity
import com.francis.maonassessment.data.model.table.TeamEntity
import com.francis.maonassessment.data.type.Plan
import com.francis.maonassessment.datasource.local.AppDatabase
import com.francis.maonassessment.datasource.remote.ApiClient
import com.francis.maonassessment.util.State
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author Francis Akpan.
 * Data store. Handles data retrieval and storage.
 */
open class Repository @Inject constructor(
    private val client: ApiClient,
    private val db: AppDatabase
){

    /**Get competitions synchronously **/
    fun getCompetitions(filter: Plan): LiveData<PagingData<CompetitionEntity>> {
        val data = db.competitionDao().getCompetitions(getFilteredQuery(filter))

        return Pager(
            config = PagingConfig(
                pageSize = 15,
                prefetchDistance = 5,
                enablePlaceholders = true,
            ),
            initialKey = null,
            pagingSourceFactory = data.asPagingSourceFactory(Dispatchers.IO)
        ).liveData
    }

    fun getTeams(id: Long): LiveData<List<TeamEntity>> =
        db.teamDao().getTeamsInCompetition(id)

    fun getSquad(id: Long): LiveData<List<PlayerEntity>> =
        db.squadDao().getTeamSquad(id)

    suspend fun fetchCompetitions(): State<*> {
        return try {
            client.service.getCompetitions().let { response ->
                if (response.isSuccessful && response.body() != null) {
                    processCompetitionsResponse(response.body())
                    State.Success
                } else State.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            State.Error(e)
        }
    }

    private suspend fun processCompetitionsResponse(body: CompetitionResponse?) {
        val competitions = body?.competitions?.map { result ->
            CompetitionEntity(
                id = result.id,
                name = result.name,
                code = result.code,
                emblemUrl = result.emblemUrl,
                plan = result.plan,
                area = result.area?.let { area ->
                    CompetitionEntity.Area(
                        area.id,
                        area.name,
                        area.countryCode,
                        area.ensignUrl
                    )
                },
                currentSeason = result.currentSeason?.let { currentSeason ->
                    CompetitionEntity.CurrentSeason(
                        currentSeason.id,
                        currentSeason.startDate,
                        currentSeason.endDate,
                        currentSeason.currentMatchDay
                    )
                }
            )
        }?.toTypedArray() ?: return
        db.competitionDao().saveCompetitions(*competitions)
    }

    suspend fun fetchTeamsData(id: Long): State<*> {
        return try {
            client.service.getTeamsInCompetition(id).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    processTeamsResponse(id, response.body())
                    State.Success
                } else State.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            State.Error(e)
        }
    }

    private suspend fun processTeamsResponse(id: Long, body: TeamResponse?) {
        val teams = body?.teams?.map { result ->
            TeamEntity(
                teamId = result.id,
                competitionId = id,
                name = result.name,
                shortName = result.shortName,
                tla = result.tla,
                crestUrl = result.crestUrl,
                address = result.address,
                phone = result.phone,
                website = result.website,
                email = result.email,
                founded = result.founded,
                clubColors = result.clubColors,
                venue = result.venue
            )
        }?.toTypedArray() ?: return
        db.teamDao().saveTeams(*teams)
    }

    suspend fun fetchSquad(id: Long): State<*> {
        return try {
            client.service.getTeamSquad(id).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    processSquadResponse(id, response.body())
                    State.Success
                } else State.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            State.Error(e)
        }
    }

    private suspend fun processSquadResponse(id: Long, body: SquadResponse?) {
        val squad = body?.squad?.map { result ->
            PlayerEntity(
                playerId = result.id,
                teamId = id,
                name = result.name,
                position = result.position,
                dateOfBirth = result.dateOfBirth,
                countryOfBirth = result.countryOfBirth,
                nationality = result.nationality,
                shirtNumber = result.shirtNumber,
                role = result.role
            )
        }?.toTypedArray() ?: return
        db.squadDao().insert(*squad)
    }

    /**
     * Construct an sql query based on the API plan selected
     * @param filter selected plan.
     * **/
    private fun getFilteredQuery(filter: Plan): SupportSQLiteQuery {
        val query = "SELECT * FROM competition WHERE plan IS '${filter.code}'"
        return SimpleSQLiteQuery(query)
    }
}