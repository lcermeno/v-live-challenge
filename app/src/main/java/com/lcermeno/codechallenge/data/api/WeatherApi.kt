package com.lcermeno.codechallenge.data.api

import com.lcermeno.codechallenge.data.api.dto.WeatherResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?current_weather=true")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): WeatherResponseDTO
}