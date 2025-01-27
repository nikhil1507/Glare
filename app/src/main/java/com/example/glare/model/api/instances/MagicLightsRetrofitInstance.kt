package com.example.glare.model.api.instances

import com.example.glare.model.api.services.GetMagicLightsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MagicLightsRetrofitInstance {
    private const val BASE_URL = "https://api.sunrise-sunset.org/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getMagicLightsService: GetMagicLightsService by lazy {
        retrofit.create(GetMagicLightsService::class.java)
    }
}