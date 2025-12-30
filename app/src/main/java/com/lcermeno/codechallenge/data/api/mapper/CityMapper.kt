package com.lcermeno.codechallenge.data.api.mapper

import com.lcermeno.codechallenge.data.api.dto.CityDTO
import com.lcermeno.codechallenge.domain.model.Resource


fun CityDTO.toDomain() = Resource.City(
    name = name,
    temperature = "",
    weatherDescription = ""
)