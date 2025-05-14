package com.github.adnanrangrej.natureguardian.ui.screens.location

sealed interface DistributionMapUiState {
    data object Loading : DistributionMapUiState
    data class Success(val clusterItems: List<SpeciesClusterItem>) : DistributionMapUiState
    data class Error(val message: String) : DistributionMapUiState
}