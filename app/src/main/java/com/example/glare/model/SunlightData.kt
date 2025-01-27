package com.example.glare.model

data class SunlightData (
    val results: Data,
    val status: String,
)

data class Data(
    val sunrise: String,
    val sunset: String,
    val first_light: String,
    val last_light: String,
    val dawn: String,
    val dusk: String,
    val solar_noon: String,
    val golden_hour: String,
)