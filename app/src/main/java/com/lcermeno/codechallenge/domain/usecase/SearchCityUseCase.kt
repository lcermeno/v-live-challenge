package com.lcermeno.codechallenge.domain.usecase

import com.lcermeno.codechallenge.data.repository.CityRepositoryImpl
import com.lcermeno.codechallenge.domain.repository.CityRepository

class SearchCityUseCase(
    private val cityRepository: CityRepository = CityRepositoryImpl()
) {
    operator fun invoke(name: String) = cityRepository.searchCity(name)
}