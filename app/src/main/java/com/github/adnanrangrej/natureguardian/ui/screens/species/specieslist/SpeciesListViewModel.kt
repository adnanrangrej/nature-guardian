package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.data.local.preferences.PrefsHelper
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.usecase.species.GetAllSpeciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeciesListViewModel @Inject constructor(
    private val getAllSpeciesUseCase: GetAllSpeciesUseCase,
    private val prefsHelper: PrefsHelper
) : ViewModel() {

    private val _showNotificationDialog = MutableStateFlow<Boolean>(false)
    val showNotificationDialog: StateFlow<Boolean> = _showNotificationDialog

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _selectedSpeciesClass =
        MutableStateFlow<SpeciesFilterChips?>(SpeciesFilterChips.Mammal)
    val selectedSpeciesClass: StateFlow<SpeciesFilterChips?> = _selectedSpeciesClass

    @OptIn(FlowPreview::class)
    val uiState: StateFlow<SpeciesListUiState> =
        combine(
            getAllSpeciesUseCase().flowOn(Dispatchers.IO),
            _query.debounce(300L).distinctUntilChanged(),
            _selectedSpeciesClass
        ) { speciesList, query, selectedSpeciesClass ->
            val classFilteredList = if (selectedSpeciesClass == null) {
                speciesList
            } else {
                speciesList.filter { species ->
                    species.species.className in selectedSpeciesClass.classNames
                }
            }

            val finalList = if (query.isEmpty()) {
                classFilteredList
            } else {
                classFilteredList.filter { species ->
                    species.species.scientificName.contains(query, ignoreCase = true)
                            || species.commonNames.any { commonName ->
                        commonName.commonName.contains(query, ignoreCase = true)
                    }
                }
            }

            SpeciesListUiState.Success(finalList) as SpeciesListUiState
        }
            .catch { throwable ->
                emit(SpeciesListUiState.Error(throwable.message ?: "Unknown Error Occurred"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SpeciesListUiState.Loading
            )

    init {
        checkIfShouldAskNotification()
    }


    fun onQueryChange(query: String) {
        _query.update {
            query
        }
    }

    fun onSpeciesClassSelected(speciesFilterChips: SpeciesFilterChips) {
        _selectedSpeciesClass.update {
            if (it == speciesFilterChips) {
                null
            } else {
                speciesFilterChips
            }
        }
    }

    fun getCommonName(detailedSpecies: DetailedSpecies): String? {
        val mainName = detailedSpecies.commonNames.find {
            it.isMain == true
        }
        return mainName?.commonName
    }

    fun getMainUrl(detailedSpecies: DetailedSpecies): String? {
        return detailedSpecies.images.firstOrNull()?.imageUrl
    }

    private fun checkIfShouldAskNotification() {
        viewModelScope.launch {
            val shouldAsk = !prefsHelper.isNotificationAsked()
            Log.d("SpeciesListVM", "Should ask for notification permission: $shouldAsk")
            if (shouldAsk) {
                _showNotificationDialog.update { true }
            }
        }
    }

    fun onNotificationDialogHandled() {
        prefsHelper.setNotificationAsked(true)
        Log.d("SpeciesListVM", "[Boolean State] Setting state back to false")
        _showNotificationDialog.update { false }
    }
}