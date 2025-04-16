package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies

@Composable
fun SpeciesDetailTabsContent(
    modifier: Modifier = Modifier,
    selectedTab: SpeciesDetailTabs,
    species: DetailedSpecies
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            when (selectedTab) {
                SpeciesDetailTabs.Overview -> SpeciesDetailOverviewTab(species)
                SpeciesDetailTabs.Habitat -> SpeciesDetailHabitatTab(species)
                SpeciesDetailTabs.Threats -> SpeciesDetailThreatsTab(species)
            }
        }
    }
}