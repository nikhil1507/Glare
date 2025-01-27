package com.example.glare.model

data class MagicLightsData(
    val results: Results,
    val status: String,
    val tzid: String
)

data class Results(
    val astronomical_twilight_begin: String,
    val astronomical_twilight_end: String,
    val civil_twilight_begin: String,
    val civil_twilight_end: String,
    val day_length: String,
    val nautical_twilight_begin: String,
    val nautical_twilight_end: String,
    val solar_noon: String,
    val sunrise: String,
    val sunset: String
)