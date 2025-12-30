package com.lcermeno.codechallenge.domain.model

sealed class Resource {
    data class City(
        val name: String,
        val temperature: String,
        val weatherDescription: String
    ): Resource()

    data object Empty: Resource()
}