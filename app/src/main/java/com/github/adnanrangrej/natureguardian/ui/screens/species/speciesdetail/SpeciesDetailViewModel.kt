package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.usecase.species.GetSpeciesByIdUseCase
import com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.geminichat.ChatBotUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SpeciesDetailViewModel @Inject constructor(
    private val getSpeciesByIdUseCase: GetSpeciesByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val internalTaxonId: Long = checkNotNull(savedStateHandle["internal_taxon_id"])
    val uiState: StateFlow<SpeciesDetailUiState> =
        getSpeciesByIdUseCase(internalTaxonId)
            .map {
                SpeciesDetailUiState.Success(it!!) as SpeciesDetailUiState
            }
            .catch { throwable ->
                emit(SpeciesDetailUiState.Error(throwable.message ?: "Unknown Error Occurred"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SpeciesDetailUiState.Loading
            )
    private val _chatUiState = MutableStateFlow<ChatBotUiState>(ChatBotUiState.Loading)
    val chatUiState: StateFlow<ChatBotUiState> = _chatUiState

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