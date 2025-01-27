package com.example.glare.model.api.services

import com.example.glare.model.MagicLightsData
import retrofit2.http.GET
import retrofit2.http.Query

interface GetMagicLightsService {
    @GET("json")

    suspend fun getMagicLights(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("date") date: String,
        @Query("formatted") toFormatDate: Int
    ): MagicLightsData
}