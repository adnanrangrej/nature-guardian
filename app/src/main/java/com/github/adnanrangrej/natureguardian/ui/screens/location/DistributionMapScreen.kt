package com.github.adnanrangrej.natureguardian.ui.screens.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DistributionMapScreen(
    viewModel: DistributionMapViewModel = hiltViewModel(),
    onClusterItemClick: (speciesId: Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    DistributionMapBody(
        uiState = uiState.value,
        onClusterItemClick = onClusterItemClick
    )
}