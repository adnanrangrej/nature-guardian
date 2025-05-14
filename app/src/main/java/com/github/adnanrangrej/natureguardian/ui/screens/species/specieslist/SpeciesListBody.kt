package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianSearchBar

@Composable
fun SpeciesListBody(
    uiState: SpeciesListUiState,
    onSpeciesClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    getCommonName: (DetailedSpecies) -> String?,
    getImageUrl: (DetailedSpecies) -> String?,
    query: String,
    onQueryChange: (String) -> Unit
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
            Column(
                modifier = modifier
            ) {
                NatureGuardianSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = query,
                    onQueryChange = onQueryChange,
                    placeHolderText = "Search species..."
                )

                SpeciesList(
                    modifier = Modifier.weight(1f),
                    species = uiState.speciesList,
                    onSpeciesClick = { onSpeciesClick(it.species.internalTaxonId) },
                    commonName = getCommonName,
                    imageUrl = getImageUrl
                )
            }
        }
    }
}