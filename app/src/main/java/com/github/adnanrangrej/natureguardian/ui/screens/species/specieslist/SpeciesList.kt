package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies

@Composable
fun SpeciesList(
    modifier: Modifier = Modifier,
    species: List<DetailedSpecies>,
    onSpeciesClick: (DetailedSpecies) -> Unit,
    commonName: (DetailedSpecies) -> String?,
    imageUrl: (DetailedSpecies) -> String?
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = modifier
    ) {
        items(
            items = species,
            key = { species -> species.species.internalTaxonId }
        ) {
            SpeciesListCard(
                modifier = Modifier.clickable { onSpeciesClick(it) },
                species = it,
                imageUrl = imageUrl(it),
                commonName = commonName(it),
            )
        }
    }
}

@Composable
fun SpeciesListShimmer(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = modifier
    ) {
        items(20) {
            SpeciesListCardShimmer()
        }
    }
}