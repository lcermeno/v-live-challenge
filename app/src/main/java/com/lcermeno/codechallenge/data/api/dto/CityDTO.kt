package com.lcermeno.codechallenge.data.api.dto

import com.google.gson.annotations.SerializedName

data class CityDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)

data class CityResponseDTO(
    @SerializedName("results")
    val results: List<CityDTO>,

    @SerializedName("generationtime_ms")
    val generationTimeMs: Double
)
