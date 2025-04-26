package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.MainGraph.NEWS
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.MainGraph.PROFILE
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.MainGraph.SPECIES
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.news.newsNavGraph
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.profile.profileNavGraph
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.species.speciesNavGraph

fun NavGraphBuilder.mainNavGraphGraph(
    navHostController: NavHostController,
    route: String,
    onLogout: () -> Unit
) {
    navigation(
        startDestination = SPECIES,
        route = route
    ) {
        speciesNavGraph(
            navHostController = navHostController,
            route = SPECIES
        )

        newsNavGraph(
            navHostController = navHostController,
            route = NEWS
        )

        profileNavGraph(
            navController = navHostController,
            route = PROFILE,
            onLogout = onLogout
        )
    }
}