package com.example.glare.model.api.services

import com.example.glare.model.SunlightData
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSunlightInfoService {
    @GET("json")
    suspend fun getSunlightInfo(
        @Query("lat") latitude: String,
        @Query("lng") longitude: String,
        @Query("date") date: String,
    ): SunlightData

}