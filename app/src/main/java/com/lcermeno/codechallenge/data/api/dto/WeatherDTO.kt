package com.lcermeno.codechallenge.data.api.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDTO(
    @SerializedName("current_weather")
    val currentWeather: CurrentWeatherDTO
)

data class CurrentWeatherDTO(
    val temperature: Double,
    val weathercode: Int
)