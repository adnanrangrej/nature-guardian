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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.ConservationStatusChip
import com.github.adnanrangrej.natureguardian.ui.components.ConservationStatusChipShimmer
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.components.shimmerEffect
import com.github.adnanrangrej.natureguardian.ui.screens.species.getDrawableResourceId
import com.github.adnanrangrej.natureguardian.ui.screens.species.getFakeSpecies
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
            .fillMaxWidth()
            .aspectRatio(3f),
        shape = SpeciesCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Species Image
                NatureGuardianImages(
                    url = imageUrl ?: "",
                    modifier = Modifier
                        .weight(0.3f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    placeholder = getDrawableResourceId(species.species.className),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(16.dp))


                // Species Details
                Column(
                    modifier = Modifier.weight(0.7f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = commonName ?: species.species.scientificName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = species.species.scientificName,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ConservationStatusChip(redlistCategory = species.species.redlistCategory)
                    }
                }
            }
        }
    }
}

@Composable
fun SpeciesListCardShimmer(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(3f),
        shape = SpeciesCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )

            Spacer(Modifier.width(16.dp))

            // Text Shimmer
            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                }

                ConservationStatusChipShimmer()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SpeciesListCardPreview(modifier: Modifier = Modifier) {

    NatureGuardianTheme(
        darkTheme = false
    ) {
        SpeciesListCard(
            modifier = modifier,
            species = getFakeSpecies(),
            imageUrl = "",
            commonName = "Tiger",
        )
    }
}

@Preview
@Composable
private fun SpeciesListCardShimmerPreview() {
    NatureGuardianTheme {
        SpeciesListCardShimmer()
    }
}

