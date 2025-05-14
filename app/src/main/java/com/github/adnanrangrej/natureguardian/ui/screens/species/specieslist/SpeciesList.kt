package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(items = species, key = { it.species.internalTaxonId }) { species ->
            SpeciesListCard(
                modifier = Modifier.clickable { onSpeciesClick(species) },
                species = species,
                imageUrl = imageUrl(species),
                commonName = commonName(species)
            )
        }
    }
}

@Composable
fun SpeciesListShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(20) {
            SpeciesListCardShimmer()
        }
    }
}