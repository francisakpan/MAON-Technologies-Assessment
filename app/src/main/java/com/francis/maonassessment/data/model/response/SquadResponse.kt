package com.francis.maonassessment.data.model.response

import androidx.annotation.Keep

@Keep
data class SquadResponse(
    val squad: List<Player>?
) {
    data class Player(
        val id: Long,
        val name: String?,
        val position: String?,
        val dateOfBirth: String?,
        val countryOfBirth: String?,
        val nationality: String?,
        val shirtNumber: String?,
        val role: String?
    )
}