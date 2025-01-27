package com.example.glare.model

data class MoonlightData(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

data class Current(
    val cloud: Int,
    val condition: Condition,
    val is_day: Int,
    val uv: Double,
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val lon: Double,
    val name: String,
    val region: String,
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

data class Forecastday(
    val astro: Astro,
    val date: String,
)

data class Astro(
    val is_moon_up: Int,
    val is_sun_up: Int,
    val moon_illumination: Int,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)
