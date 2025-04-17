package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.components.shimmerEffect
import com.github.adnanrangrej.natureguardian.ui.screens.species.getDrawableResourceId
import com.github.adnanrangrej.natureguardian.ui.screens.species.getFakeSpecies
import com.github.adnanrangrej.natureguardian.ui.screens.species.getStatusColor
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
                .aspectRatio(1f)
                .padding(16.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
            placeholder = getDrawableResourceId(species.species.className),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))


        // Species Details
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = commonName ?: species.species.scientificName,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = species.species.scientificName,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row {
                SuggestionChip(
                    onClick = {},
                    label = {
                        Text(
                            text = species.species.redlistCategory,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = getStatusColor(species.species.redlistCategory),
                        labelColor = Color.White
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

@Preview(showBackground = true)
@Composable
fun SpeciesListCardPreview(modifier: Modifier = Modifier) {

    NatureGuardianTheme(
        darkTheme = false
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

