package com.lcermeno.codechallenge.domain.repository

import com.lcermeno.codechallenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun searchCity(name: String): Flow<Resource>
}