package com.lcermeno.codechallenge.data.repository

import com.lcermeno.codechallenge.data.api.CityApi
import com.lcermeno.codechallenge.data.api.WeatherApi
import com.lcermeno.codechallenge.data.api.dto.CityDTO
import com.lcermeno.codechallenge.data.api.dto.CityResponseDTO
import com.lcermeno.codechallenge.data.api.dto.CurrentWeatherDTO
import com.lcermeno.codechallenge.data.api.dto.WeatherResponseDTO
import com.lcermeno.codechallenge.domain.model.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CityRepositoryImplTest {

    private val cityApi: CityApi = mockk()
    private val weatherApi: WeatherApi = mockk()

    private lateinit var repository: CityRepositoryImpl

    @Before
    fun setup() {
        repository = CityRepositoryImpl(cityApi, weatherApi)
    }

    @Test
    fun `searchCity should emit Result City when both APIs succeed`() = runTest {
        // GIVEN
        val cityName = "San Francisco"
        val mockCityDto = CityDTO(
            id = 1,
            name = cityName,
            latitude = 37.77,
            longitude = -122.41
        )
        val cityResponse = CityResponseDTO(results = listOf(mockCityDto), generationTimeMs = 0.5)

        val weatherResponse = WeatherResponseDTO(
            currentWeather = CurrentWeatherDTO(temperature = 18.5, weathercode = 3)
        )

        coEvery { cityApi.searchCity(cityName) } returns cityResponse
        coEvery { weatherApi.getWeather(37.77, -122.41) } returns weatherResponse

        // WHEN
        val result = repository.searchCity(cityName).first()

        // THEN
        assert(result is Resource.City)
        val cityResult = result as Resource.City
        assertEquals(cityName, cityResult.name)
        assertEquals("18.5°C", cityResult.temperature)
        assertEquals("Code: 3", cityResult.weatherDescription)
    }

    @Test
    fun `searchCity should emit Result Empty when no cities are found`() = runTest {
        // GIVEN
        val cityName = "NonExistentCity"
        coEvery { cityApi.searchCity(cityName) } returns CityResponseDTO(emptyList(), 0.0)

        // WHEN
        val result = repository.searchCity(cityName).first()

        // THEN
        assertEquals(Resource.Empty, result)
    }

    @Test
    fun `searchCity should emit Result Empty when an exception occurs`() = runTest {
        // GIVEN
        coEvery { cityApi.searchCity(any()) } throws Exception("Network Failure")

        // WHEN
        val result = repository.searchCity("Madrid").first()

        // THEN
        assertEquals(Resource.Empty, result)
    }
}