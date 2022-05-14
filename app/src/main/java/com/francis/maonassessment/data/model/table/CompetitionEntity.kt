package com.francis.maonassessment.data.model.table

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition")
data class CompetitionEntity(
    @PrimaryKey
    val id: Long,
    @Embedded
    val area: Area?,
    val name: String?,
    val code: String?,
    val emblemUrl: String?,
    val plan: String?,
    @Embedded
    val currentSeason: CurrentSeason?
) {
    data class Area(
        val areaId: Long,
        val areaName: String?,
        val countryCode: String?,
        val ensignUrl: String?
    )

    data class CurrentSeason(
        val seasonId: Long,
        val startDate: String?,
        val endDate: String?,
        val currentMatchDay: Int?,
    )
}