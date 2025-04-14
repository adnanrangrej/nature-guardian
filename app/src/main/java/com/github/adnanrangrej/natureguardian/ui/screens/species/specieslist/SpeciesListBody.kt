package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen

@Composable
fun SpeciesListBody(
    uiState: SpeciesListUiState,
    onSpeciesClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    getCommonName: (DetailedSpecies) -> String?,
    getImageUrl: (DetailedSpecies) -> String?,
) {
    when (uiState) {
        is SpeciesListUiState.Error -> {
            ErrorScreen(
                retryAction = {},
                modifier = modifier,
                errorMessage = R.string.db_error
            )
        }

        SpeciesListUiState.Loading -> {
            SpeciesListShimmer(modifier)
        }

        is SpeciesListUiState.Success -> {
            SpeciesList(
                modifier = modifier,
                species = uiState.speciesList,
                onSpeciesClick = { onSpeciesClick(it.species.internalTaxonId) },
                commonName = getCommonName,
                imageUrl = getImageUrl
            )
        }
    }
}