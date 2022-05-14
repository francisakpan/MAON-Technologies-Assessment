package com.francis.maonassessment.data.model.response

import androidx.annotation.Keep

@Keep
data class TeamResponse(
    val count: Int,
    val teams: List<Team>?
) {
    data class Team(
        val id: Long,
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
    )
}