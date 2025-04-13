package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import com.github.adnanrangrej.natureguardian.domain.model.species.Species

sealed interface SpeciesListUiState {
    object Loading : SpeciesListUiState
    data class Success(val speciesList: List<Species> = listOf()) : SpeciesListUiState
    data class Error(val message: String? = null) : SpeciesListUiState
}