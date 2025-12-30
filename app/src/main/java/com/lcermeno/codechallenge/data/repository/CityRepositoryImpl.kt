package com.lcermeno.codechallenge.data.repository

import com.lcermeno.codechallenge.data.api.CityApi
import com.lcermeno.codechallenge.data.api.WeatherApi
import com.lcermeno.codechallenge.di.AppModule
import com.lcermeno.codechallenge.domain.model.Resource
import com.lcermeno.codechallenge.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CityRepositoryImpl(
    private val cityApi: CityApi = AppModule.cityApi,
    private val weatherApi: WeatherApi = AppModule.weatherApi
): CityRepository {

    override fun searchCity(name: String): Flow<Resource> = flow {
        val cityResponse = cityApi.searchCity(name)
        val firstCity = cityResponse.results.firstOrNull()

        if (firstCity != null) {
            val weatherResult = try {
                val weather = weatherApi.getWeather(firstCity.latitude, firstCity.longitude)
                Pair("${weather.currentWeather.temperature}°C", "Code: ${weather.currentWeather.weathercode}")
            } catch (e: Exception) {
                Pair("N/A", "Weather info unavailable")
            }

            emit(Resource.City(
                name = firstCity.name,
                temperature = weatherResult.first,
                weatherDescription = weatherResult.second
            ))
        } else {
            emit(Resource.Empty)
        }
    }.catch { e ->
        emit(Resource.Empty)
    }
}