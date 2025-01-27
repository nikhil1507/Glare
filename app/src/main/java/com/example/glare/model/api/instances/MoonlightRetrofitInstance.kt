package com.example.glare.model.api.instances

import com.example.glare.model.api.services.GetMoonlightInfoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoonlightRetrofitInstance {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    private val retrofit:Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val getMoonlightInfoService: GetMoonlightInfoService by lazy {
        retrofit.create(GetMoonlightInfoService::class.java)
    }

}