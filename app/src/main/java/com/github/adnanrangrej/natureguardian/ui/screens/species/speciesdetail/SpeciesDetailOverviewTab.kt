package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Science
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.AnimatedInfoCard
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun SpeciesDetailOverviewTab(
    species: DetailedSpecies
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Description Section
        AnimatedInfoCard(
            title = "Description",
            icon = Icons.Rounded.Description
        ) {
            MarkdownText(
                markdown = species.details?.description ?: "No description available",
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Justify
                ),
            )
        }

        // Taxonomy Section
        AnimatedInfoCard(
            title = "Taxonomy",
            icon = Icons.Rounded.Science
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaxonomyRow("Kingdom", species.species.kingdomName)
                TaxonomyRow("Phylum", species.species.phylumName)
                TaxonomyRow("Class", species.species.className)
                TaxonomyRow("Order", species.species.orderName)
                TaxonomyRow("Family", species.species.familyName)
                TaxonomyRow("Genus", species.species.genusName)
                TaxonomyRow("Species", species.species.speciesEpithet)
            }
        }
    }
}

@Composable
private fun TaxonomyRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}