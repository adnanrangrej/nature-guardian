package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
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
class SpeciesListViewModel @Inject constructor(
    private val getAllSpeciesUseCase: GetAllSpeciesUseCase,
) : ViewModel() {

    val uiState: StateFlow<SpeciesListUiState> =
        getAllSpeciesUseCase()
            .flowOn(Dispatchers.IO)
            .map { speciesList ->
                SpeciesListUiState.Success(speciesList) as SpeciesListUiState
            }
            .catch { throwable ->
                emit(SpeciesListUiState.Error(throwable.message ?: "Unknown Error Occurred"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SpeciesListUiState.Loading
            )

    fun getCommonName(detailedSpecies: DetailedSpecies): String? {
        val mainName = detailedSpecies.commonNames.find {
            it.isMain == true
        }
        return mainName?.commonName
    }

    fun getMainUrl(detailedSpecies: DetailedSpecies): String? {
        return detailedSpecies.images.firstOrNull()?.imageUrl
    }
}