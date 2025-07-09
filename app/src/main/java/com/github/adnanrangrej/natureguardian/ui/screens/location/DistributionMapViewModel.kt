package com.github.adnanrangrej.natureguardian.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.usecase.species.GetAllSpeciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DistributionMapViewModel @Inject constructor(
    private val getAllSpeciesUseCase: GetAllSpeciesUseCase
) : ViewModel() {
    val uiState: StateFlow<DistributionMapUiState> =
        getAllSpeciesUseCase().flowOn(Dispatchers.IO)
            .map { speciesList ->
                val clusterItems = mutableListOf<SpeciesClusterItem>()
                speciesList.forEach { species ->
                    val speciesId = species.species.internalTaxonId
                    val scientificName = species.species.scientificName
                    species.locations.forEach { location ->
                        val latitude = location.latitude
                        val longitude = location.longitude
                        clusterItems.add(
                            SpeciesClusterItem(
                                speciesId = speciesId,
                                speciesName = scientificName,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }
                DistributionMapUiState.Success(clusterItems = clusterItems) as DistributionMapUiState

            }
            .catch { throwable ->
                emit(DistributionMapUiState.Error(throwable.message ?: "Unknown Error Occurred"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DistributionMapUiState.Loading
            )
}