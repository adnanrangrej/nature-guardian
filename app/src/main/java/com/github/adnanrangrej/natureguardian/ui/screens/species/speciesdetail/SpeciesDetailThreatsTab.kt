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
import androidx.compose.material.icons.rounded.ReportProblem
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
import com.github.adnanrangrej.natureguardian.domain.model.species.Threat
import com.github.adnanrangrej.natureguardian.ui.components.AnimatedInfoCard

@Composable
fun SpeciesDetailThreatsTab(species: DetailedSpecies) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedInfoCard(
            title = "Threats",
            icon = Icons.Rounded.ReportProblem
        ) {
            if (species.threats.isEmpty()) {
                Text("No threat data available", style = MaterialTheme.typography.bodyLarge)
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    species.threats.forEach { threat ->
                        ExpandableThreatItem(threat)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableThreatItem(threat: Threat) {
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
                    imageVector = Icons.Rounded.ReportProblem,
                    contentDescription = "Threat Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = threat.threatName,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    threat.severity?.let {
                        Text("Severity: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    threat.stressName?.let {
                        Text("Stress: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}