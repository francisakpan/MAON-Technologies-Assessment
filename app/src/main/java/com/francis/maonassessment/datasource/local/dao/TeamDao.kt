package com.francis.maonassessment.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.francis.maonassessment.data.model.table.TeamEntity

@Dao
interface TeamDao {

    @Transaction
    @Query("SELECT * FROM teams WHERE competitionId = :competitionId")
    fun getTeamsInCompetition(competitionId: Long): LiveData<List<TeamEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTeams(vararg team: TeamEntity)

    @Query("DELETE FROM teams")
    suspend fun nuke()

}