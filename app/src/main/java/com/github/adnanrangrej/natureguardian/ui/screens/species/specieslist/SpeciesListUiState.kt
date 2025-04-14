package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies

sealed interface SpeciesListUiState {
    object Loading : SpeciesListUiState
    data class Success(
        val speciesList: List<DetailedSpecies> = listOf(),
    ) : SpeciesListUiState

    data class Error(val message: String? = null) : SpeciesListUiState
}