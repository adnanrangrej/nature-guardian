package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesDetailScreen(
    viewModel: SpeciesDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold { innerPadding ->
        SpeciesDetailBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            uiState = uiState.value,
            getCommonName = viewModel::getCommonName,
            getImageUrl = viewModel::getMainUrl,
            navigateUp = navigateUp
        )
    }
}