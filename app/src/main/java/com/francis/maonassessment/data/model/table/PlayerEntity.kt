package com.francis.maonassessment.data.model.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "squad")
data class PlayerEntity(
    @PrimaryKey
    val playerId: Long,
    val teamId: Long,
    val name: String?,
    val position: String?,
    val dateOfBirth: String?,
    val countryOfBirth: String?,
    val nationality: String?,
    val shirtNumber: String?,
    val role: String?
)