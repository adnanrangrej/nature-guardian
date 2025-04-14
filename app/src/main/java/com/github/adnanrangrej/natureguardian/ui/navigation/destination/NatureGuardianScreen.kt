package com.github.adnanrangrej.natureguardian.ui.navigation.destination

sealed class NatureGuardianScreen(
    val route: String,
    val title: String,
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true,
    val canNavigateBack: Boolean,
) {
    object SpeciesList :
        NatureGuardianScreen(route = "species", title = "Species", showTopBar = true, showBottomBar = true, canNavigateBack = false)

    object SpeciesDetail : NatureGuardianScreen(
        route = "species/{internal_taxon_id}",
        title = "Species",
        showTopBar = true,
        showBottomBar = false,
        canNavigateBack = true
    ) {
        fun createRoute(internalTaxonId: Long) = "species/$internalTaxonId"
    }

    object NewsList :
        NatureGuardianScreen(route = "news", title = "News", showTopBar = true, showBottomBar = true, canNavigateBack = false)

    object NewsDetail : NatureGuardianScreen(
        route = "news/{timestamp}",
        title = "News Detail",
        showTopBar = true,
        showBottomBar = false,
        canNavigateBack = true
    ) {
        fun createRoute(timestamp: String) = "news/$timestamp"
    }

    object Map : NatureGuardianScreen(
        route = "map/{scientific_name}",
        title = "Map",
        showTopBar = true,
        showBottomBar = false,
        canNavigateBack = true
    ) {
        fun createRoute(scientificName: String) = "map/$scientificName"
    }

    object Profile :
        NatureGuardianScreen(route = "profile", title = "Profile", showTopBar = true, showBottomBar = true, canNavigateBack = false)
}