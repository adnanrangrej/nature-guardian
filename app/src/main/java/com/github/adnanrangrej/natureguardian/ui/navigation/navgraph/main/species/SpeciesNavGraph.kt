package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.species

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.species.SpeciesNavigation
import com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.SpeciesDetailScreen
import com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist.SpeciesListScreen

fun NavController.navigateToSpeciesDetail(internalTaxonId: Long) {
    navigate(SpeciesNavigation.SpeciesDetail.createRoute(internalTaxonId))
}

fun NavGraphBuilder.speciesNavGraph(
    navHostController: NavHostController,
    route: String
) {
    navigation(
        route = route,
        startDestination = SpeciesNavigation.SpeciesList.route
    ) {
        speciesListRoute(navigateToSpeciesDetail = navHostController::navigateToSpeciesDetail)
        speciesDetailRoute(onNavigateUp = navHostController::navigateUp)

    }
}

fun NavGraphBuilder.speciesListRoute(navigateToSpeciesDetail: (Long) -> Unit) {
    composable(route = SpeciesNavigation.SpeciesList.route) {
        SpeciesListScreen(navigateToSpeciesDetail = navigateToSpeciesDetail)
    }
}

fun NavGraphBuilder.speciesDetailRoute(
    onNavigateUp: () -> Unit
) {
    composable(
        route = SpeciesNavigation.SpeciesDetail.route,
        arguments = listOf(
            navArgument("internal_taxon_id") {
                type = NavType.LongType
            }
        )
    ) {
        SpeciesDetailScreen(onNavigateUp = onNavigateUp)
    }
}