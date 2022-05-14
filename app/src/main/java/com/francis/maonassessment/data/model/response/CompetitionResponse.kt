package com.francis.maonassessment.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CompetitionResponse(
    val count: Int,
    val competitions: List<Competition>?
) {
    data class Competition(
        val id: Long,
        val area: Area?,
        val name: String?,
        val code: String?,
        val emblemUrl: String?,
        val plan: String?,
        val currentSeason: CurrentSeason?
    ) {
        data class Area(
            val id: Long,
            val name: String?,
            val countryCode: String?,
            val ensignUrl: String?
        )

        data class CurrentSeason(
            val id: Long,
            val startDate: String?,
            val endDate: String?,
            @SerializedName("currentMatchday")
            val currentMatchDay: Int,
        )
    }
}