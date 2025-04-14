package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SpeciesListScreen(
    viewModel: SpeciesListViewModel = hiltViewModel(),
    navigateToSpeciesDetail: (Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    SpeciesListBody(
        uiState = uiState.value,
        onSpeciesClick = navigateToSpeciesDetail,
        modifier = Modifier.fillMaxSize(),
        getCommonName = viewModel::getCommonName,
        getImageUrl = viewModel::getMainUrl,
    )
}