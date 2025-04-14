package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.species.CommonName
import com.github.adnanrangrej.natureguardian.domain.model.species.ConservationAction
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.Habitat
import com.github.adnanrangrej.natureguardian.domain.model.species.Location
import com.github.adnanrangrej.natureguardian.domain.model.species.Species
import com.github.adnanrangrej.natureguardian.domain.model.species.SpeciesDetail
import com.github.adnanrangrej.natureguardian.domain.model.species.SpeciesImage
import com.github.adnanrangrej.natureguardian.domain.model.species.Threat
import com.github.adnanrangrej.natureguardian.domain.model.species.UseTrade
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.components.shimmerEffect
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme
import com.github.adnanrangrej.natureguardian.ui.theme.SpeciesCardShape

@Composable
fun SpeciesListCard(
    modifier: Modifier = Modifier,
    species: DetailedSpecies,
    imageUrl: String?,
    commonName: String?
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        shape = SpeciesCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        // Species Image
        NatureGuardianImages(
            url = imageUrl ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            placeholder = getDrawableResourceId(species.species.className),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(16.dp))


        // Species Details
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = commonName ?: species.species.scientificName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = species.species.scientificName,
                style = MaterialTheme.typography.bodyMedium
            )

            Row {
                SuggestionChip(
                    onClick = {},
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(MaterialTheme.colorScheme.onSurface, CircleShape)
                        )
                    },
                    label = {
                        Text(
                            text = species.species.redlistCategory,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = getStatusColor(species.species.redlistCategory)
                    )
                )
            }
        }
    }
}

@Composable
fun SpeciesListCardShimmer(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp),
        shape = SpeciesCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        // Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
        Spacer(Modifier.height(16.dp))

        // Detail
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmerEffect()
            )

            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmerEffect()
            )
        }
    }
}

fun getFakeSpecies(): DetailedSpecies {
    val species = Species(
        internalTaxonId = 12345,
        scientificName = "Imaginary Species",
        redlistCategory = "Endangered", // Least Concern
        redlistCriteria = "N/A",
        kingdomName = "Animalia",
        phylumName = "Chordata",
        className = "MAMMALIA",
        orderName = "Primates",
        familyName = "Hominidae",
        genusName = "Homo",
        speciesEpithet = "sapiens",
        doi = "10.0000/fake-doi-12345",
        populationTrend = "Stable",
        hasImage = true,
        isBookmarked = false
    )

    val details = SpeciesDetail(
        speciesId = 12345,
        description = "A completely fictional species created for testing purposes.  It is said to have unique adaptations.",
        conservationActionsDescription = "Protect its imaginary habitat and reduce pollution.",
        habitatDescription = "Lives in the lush, imaginary forests of Fantasia.",
        useTradeDescription = "No known use or trade.",
        threatsDescription = "Habitat loss due to encroaching imaginationlessness.",
        populationDescription = "Estimated to be around 1,000,000 individuals."
    )

    val commonNames = listOf(
        CommonName(1, 12345, "Fake Creature", "en", true),
        CommonName(2, 12345, "Imaginario", "es", false),
        CommonName(3, 12345, "Fabelwesen", "de", false)
    )

    val conservationActions = listOf(
        ConservationAction(101, 12345, "PA", "Protected Area Establishment"),
        ConservationAction(102, 12345, "CC", "Climate Change Mitigation"),
        ConservationAction(103, 12345, "RA", "Research and Awareness")
    )

    val habitats = listOf(
        Habitat(201, 12345, "TF", "Tropical Forest", true, "Year-round", "Optimal"),
        Habitat(202, 12345, "MG", "Montane Grassland", false, "Summer", "Suitable")
    )

    val locations = listOf(
        Location(301, 12345, 25.0, 120.0),
        Location(302, 12345, -10.0, 140.0),
        Location(303, 12345, 50.0, -70.0)
    )

    val threats = listOf(
        Threat(401, 12345, "HL", "Habitat Loss", "UD", "Urban Development", "High"),
        Threat(402, 12345, "CC", "Climate Change", "DR", "Drought", "Medium"),
        Threat(403, 12345, "PO", "Pollution", "WA", "Water Pollution", "Low")
    )

    val useTrade = listOf(
        UseTrade(501, 12345, "NU", "Non-use", false, false),
        UseTrade(502, 12345, "RS", "Research", true, true)
    )

    val images = listOf(
        SpeciesImage(601, 12345, "https://example.com/image1.jpg"), // Replace with actual URLs
        SpeciesImage(602, 12345, "https://example.com/image2.jpg")
    )

    return DetailedSpecies(
        species,
        details,
        commonNames,
        conservationActions,
        habitats,
        locations,
        threats,
        useTrade,
        images
    )
}

@Preview(showBackground = true)
@Composable
fun SpeciesListCardPreview(modifier: Modifier = Modifier) {

    NatureGuardianTheme(
        darkTheme = true
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpeciesListCard(
                modifier = modifier,
                species = getFakeSpecies(),
                imageUrl = "",
                commonName = "Tiger",
            )
        }
    }
}

@Preview
@Composable
private fun SpeciesListCardShimmerPreview() {
    NatureGuardianTheme {
        SpeciesListCardShimmer()
    }
}


fun getStatusColor(status: String): Color {
    return when (status) {
        "Critically Endangered" -> Color(0xFFFF0000)
        "Endangered" -> Color(0xFFFFA500)
        "Vulnerable" -> Color(0xFFFFFF00)
        else -> Color(0xFF808080)
    }
}

fun getDrawableResourceId(className: String): Int {
    return when (className) {
        "AMPHIBIA" -> R.drawable.amphibian_outlined
        "AVES" -> R.drawable.bird_placeholder
        "MAMMALIA" -> R.drawable.mammals_outlined
        else -> R.drawable.ic_broken_image
    }
}
