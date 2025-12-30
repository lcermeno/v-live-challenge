package com.lcermeno.codechallenge.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcermeno.codechallenge.domain.model.Resource
import com.lcermeno.codechallenge.domain.usecase.SearchCityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase = SearchCityUseCase(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    fun search() {

        val errors = _uiState.value.hasErrors()

        if (errors == null) {
            searchCityUseCase(_uiState.value.search)
                .onStart { _uiState.update { it.copy(loading = true) } }
                .onEach { result ->
                    when (result) {
                        is Resource.City -> _uiState.update {
                            it.copy(
                                cityName = result.name,
                                temperature = result.temperature,
                                weatherDescription = result.weatherDescription,
                                loading = false
                            )
                        }

                        is Resource.Empty -> _uiState.update {
                            it.copy(
                                errorMessage = "Empty results",
                                loading = false
                            )
                        }
                    }
                }
                .launchIn(viewModelScope)
        } else {
            _uiState.update {
                it.copy(errorMessage = errors)
            }
        }
    }

    fun onSearchChanged(value: String) {
        _uiState.update { it.copy(search = value) }
    }

    fun clearErrors() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

private fun SearchState.hasErrors() =
    if (search.isEmpty()) "Search field empty"
    else null
