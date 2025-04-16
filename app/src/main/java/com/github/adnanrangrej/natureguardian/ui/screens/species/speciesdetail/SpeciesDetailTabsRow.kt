package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun SpeciesDetailTabsRow(
    modifier: Modifier = Modifier,
    tabsTitle: List<String>,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        indicator = { },
        divider = { }
    ) {
        tabsTitle.forEachIndexed { index, title ->
            val isSelected = pagerState.currentPage == index
            val color =
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            val textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None

            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        color = color,
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = textDecoration,
                        fontWeight = fontWeight,
                    )
                }
            )
        }
    }
}