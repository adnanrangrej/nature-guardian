package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Terrain
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.Habitat
import com.github.adnanrangrej.natureguardian.ui.components.AnimatedInfoCard

@Composable
fun SpeciesDetailHabitatTab(species: DetailedSpecies) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedInfoCard(
            title = "Habitats",
            icon = Icons.Rounded.Terrain
        ) {
            if (species.habitats.isEmpty()) {
                Text("No habitat data available", style = MaterialTheme.typography.bodyLarge)
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    species.habitats.forEach { habitat ->
                        ExpandableHabitatItem(habitat)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableHabitatItem(habitat: Habitat) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Terrain,
                    contentDescription = "Habitat Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = habitat.habitatName,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    habitat.season?.let {
                        Text("Season: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    habitat.suitability?.let {
                        Text("Suitability: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    habitat.majorImportance?.let {
                        Text(
                            "Major Importance: ${if (it) "Yes" else "No"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
