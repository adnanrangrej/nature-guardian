package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.location

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.location.LocationNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.species.navigateToSpeciesDetail
import com.github.adnanrangrej.natureguardian.ui.screens.location.DistributionMapScreen

fun NavGraphBuilder.speciesLocationNavGraph(
    navController: NavHostController,
    route: String
) {
    navigation(
        startDestination = LocationNavigation.LocationMap.route,
        route = route,
    ) {
        locationMapRoute(navController::navigateToSpeciesDetail)
    }
}

private fun NavGraphBuilder.locationMapRoute(navigateToSpeciesDetail: (Long) -> Unit) {
    composable(route = LocationNavigation.LocationMap.route) {
        DistributionMapScreen(onClusterItemClick = navigateToSpeciesDetail)
    }
}