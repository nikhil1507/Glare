package com.example.glare.model.repository

import com.example.glare.model.MagicLightsData
import com.example.glare.model.MoonlightData
import com.example.glare.model.SunlightData
import com.example.glare.model.api.instances.MagicLightsRetrofitInstance
import com.example.glare.model.api.instances.MoonlightRetrofitInstance
import com.example.glare.model.api.instances.SunlightRetrofitInstance

class ApiRepository {
    private val sunlightService = SunlightRetrofitInstance.getSunlightInfoService
    private val moonlightService = MoonlightRetrofitInstance.getMoonlightInfoService
    private val magicLightsService = MagicLightsRetrofitInstance.getMagicLightsService

    suspend fun getSunlightInfo(latitude: String, longitude: String, date: String): SunlightData {
        return sunlightService.getSunlightInfo(latitude, longitude, date)
    }

    suspend fun getMoonlightInfo(key:String,query:String) : MoonlightData {
        return moonlightService.getMoonlightInfo(key, query)
    }

    suspend fun getMagicLights(latitude:Double, longitude:Double,date: String): MagicLightsData {
        return magicLightsService.getMagicLights(latitude,longitude,date, toFormatDate = 0)
    }
 }