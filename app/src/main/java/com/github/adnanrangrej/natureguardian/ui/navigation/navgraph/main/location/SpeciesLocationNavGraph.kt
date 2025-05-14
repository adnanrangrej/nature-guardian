package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.location

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.location.LocationNavigation
import com.github.adnanrangrej.natureguardian.ui.screens.location.SpeciesDistributionMapScreen

fun NavGraphBuilder.speciesLocationNavGraph(
    route: String
) {
    navigation(
        startDestination = LocationNavigation.LocationMap.route,
        route = route,
    ) {
        locationMapRoute()
    }
}

private fun NavGraphBuilder.locationMapRoute() {
    composable(route = LocationNavigation.LocationMap.route) {
        SpeciesDistributionMapScreen()
    }
}