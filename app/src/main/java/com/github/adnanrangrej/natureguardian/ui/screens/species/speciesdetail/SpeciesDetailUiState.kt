package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies

sealed interface SpeciesDetailUiState {
    data class Success(val species: DetailedSpecies) : SpeciesDetailUiState
    data class Error(val message: String? = null) : SpeciesDetailUiState
    object Loading : SpeciesDetailUiState
}