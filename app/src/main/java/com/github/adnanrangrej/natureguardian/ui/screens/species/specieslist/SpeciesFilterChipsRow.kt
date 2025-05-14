package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpeciesFilterChipsRow(
    selectedSpeciesClass: SpeciesFilterChips?,
    onSpeciesClassSelected: (SpeciesFilterChips) -> Unit,
    modifier: Modifier = Modifier
) {
    val chips = SpeciesFilterChips.entries.toTypedArray()
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(chips) { chip ->
            FilterChip(
                selected = chip == selectedSpeciesClass,
                onClick = { onSpeciesClassSelected(chip) },
                label = {
                    Text(text = chip.displayName)
                }
            )
        }
    }
}