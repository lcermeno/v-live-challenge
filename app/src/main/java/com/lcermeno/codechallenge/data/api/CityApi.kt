package com.lcermeno.codechallenge.data.api

import com.lcermeno.codechallenge.data.api.dto.CityResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {

    @GET("/v1/search")
    suspend fun searchCity(@Query("name") name: String): CityResponseDTO

}