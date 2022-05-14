package com.francis.maonassessment.datasource.remote

import retrofit2.Retrofit
import javax.inject.Inject

open class ApiClient @Inject constructor(
    private val retrofit: Retrofit
) {

    val service: Service
        get() = retrofit.create(Service::class.java)
}