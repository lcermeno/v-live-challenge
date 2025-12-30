package com.lcermeno.codechallenge.presentation.search

data class SearchState(
    val search: String = "",
    val cityName: String = "",
    val temperature: String = "",
    val weatherDescription: String = "",
    val errorMessage: String? = null,
    val loading: Boolean = false
)