package com.francis.maonassessment.datasource.local.dao

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.francis.maonassessment.data.model.table.CompetitionEntity

@Dao
interface CompetitionDao {

    @RawQuery(observedEntities = [CompetitionEntity::class])
    fun getCompetitions(query: SupportSQLiteQuery): DataSource.Factory<Int, CompetitionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCompetitions(vararg entity: CompetitionEntity)

    @Query("DELETE FROM competition")
    suspend fun nuke()
}