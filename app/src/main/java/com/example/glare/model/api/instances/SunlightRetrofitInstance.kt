package com.example.glare.model.api.instances

import com.example.glare.model.api.services.GetSunlightInfoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SunlightRetrofitInstance {
    private const val BASE_URL = "https://api.sunrisesunset.io/"

    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val getSunlightInfoService: GetSunlightInfoService by lazy {
        retrofit.create(GetSunlightInfoService::class.java)
    }
}