package com.github.adnanrangrej.natureguardian.ui.screens.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen

@Composable
fun DistributionMapBody(
    uiState: DistributionMapUiState,
    modifier: Modifier = Modifier,
    onClusterItemClick: (speciesId: Long) -> Unit
) {
    when (uiState) {
        is DistributionMapUiState.Error -> {
            ErrorScreen(
                retryAction = {},
                modifier = modifier,
                errorMessage = R.string.db_error
            )
        }

        DistributionMapUiState.Loading -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is DistributionMapUiState.Success -> {
            SpeciesDistributionMap(
                modifier = modifier,
                clusterItems = uiState.clusterItems,
                onClusterItemClick = onClusterItemClick
            )
        }
    }

}