package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesDetailBody(
    modifier: Modifier = Modifier,
    uiState: SpeciesDetailUiState,
    getCommonName: (DetailedSpecies) -> String?,
    getImageUrl: (DetailedSpecies) -> String?,
) {
    when (uiState) {
        is SpeciesDetailUiState.Error -> {
            ErrorScreen(
                retryAction = {},
                modifier = modifier,
                errorMessage = R.string.db_error
            )
        }

        SpeciesDetailUiState.Loading -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is SpeciesDetailUiState.Success -> {
            SpeciesDetailCard(
                modifier = modifier,
                species = uiState.species,
                commonName = getCommonName(uiState.species),
                imageUrl = getImageUrl(uiState.species)
            )
        }
    }
}