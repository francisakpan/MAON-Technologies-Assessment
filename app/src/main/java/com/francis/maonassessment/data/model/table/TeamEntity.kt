package com.francis.maonassessment.data.model.table

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    val teamId: Long,
    val competitionId: Long?,
    val name: String?,
    val shortName: String?,
    val tla: String?,
    val crestUrl: String?,
    val address: String?,
    val phone: String?,
    val website: String?,
    val email: String?,
    val founded: String?,
    val clubColors: String?,
    val venue: String?
): Parcelable