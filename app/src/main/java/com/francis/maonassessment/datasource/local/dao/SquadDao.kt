package com.francis.maonassessment.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.francis.maonassessment.data.model.table.PlayerEntity

@Dao
interface SquadDao {

    @Query("SELECT * FROM squad WHERE teamId = :id")
    fun getTeamSquad(id: Long): LiveData<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg player: PlayerEntity)

    @Query("DELETE FROM squad")
    suspend fun nuke()
}