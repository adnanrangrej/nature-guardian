package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.screens.species.getFakeSpecies
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpeciesDetailCard(
    modifier: Modifier = Modifier,
    species: DetailedSpecies,
    commonName: String?,
    imageUrl: String?
) {

    val tabsTitles = SpeciesDetailTabs.entries.map { it.name }

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { tabsTitles.size }
    )

    // For parallax scrolling effect
    val headerHeight = 350.dp
    val listState = rememberLazyListState()



    LazyColumn(
        modifier = modifier,
        state = listState

    ) {
        item {
            // Space for the header image
            ParallaxImage(
                imageHeight = headerHeight,
                listState = listState,
                imageUrl = imageUrl ?: "",
                scientificName = species.species.scientificName,
                commonName = commonName ?: species.species.scientificName,
                redListCategory = species.species.redlistCategory,
                className = species.species.className,
                doi = species.species.doi
            )
        }

        stickyHeader {
            SpeciesDetailTabsRow(
                modifier = Modifier.fillMaxWidth(),
                tabsTitle = tabsTitles,
                pagerState = pagerState,
                onTabSelected = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                }
            )
        }

        item {
            // Tabs content
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp * 2),
                state = pagerState,
            ) { page ->
                SpeciesDetailTabsContent(
                    modifier = Modifier.fillMaxSize(),
                    selectedTab = SpeciesDetailTabs.entries[page],
                    species = species
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SpeciesDetailCardPreview() {
    NatureGuardianTheme(
        darkTheme = false
    ) {
        SpeciesDetailCard(
            modifier = Modifier.fillMaxSize(),
            species = getFakeSpecies(),
            commonName = "Lion",
            imageUrl = ""
        )
    }
}