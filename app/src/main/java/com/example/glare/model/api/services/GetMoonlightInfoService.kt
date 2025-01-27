package com.example.glare.model.api.services

import com.example.glare.model.MoonlightData
import retrofit2.http.GET
import retrofit2.http.Query

interface GetMoonlightInfoService {
    @GET("forecast.json")
    suspend fun getMoonlightInfo(
        @Query("key") key:String,
        @Query("q") query: String
    ) : MoonlightData
}