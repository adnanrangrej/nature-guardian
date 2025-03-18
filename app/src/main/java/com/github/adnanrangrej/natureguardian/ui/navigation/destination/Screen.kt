package com.github.adnanrangrej.natureguardian.ui.navigation.destination

sealed class Screen(val route: String, val title: String) {
    object SpeciesScreen : Screen("species", "Species")
    object SpeciesDetailScreen : Screen("species/{scientific_name}", "Species") {
        fun createRoute(scientificName: String) = "species/$scientificName"
    }

    object NewsScreen : Screen("news", "News")
    object NewsDetailScreen : Screen("news/{timestamp}", "News Detail") {
        fun createRoute(timestamp: String) = "news/$timestamp"
    }

    object MapScreen : Screen("map/{scientific_name}", "Map") {
        fun createRoute(scientificName: String) = "map/$scientificName"
    }

    object ProfileScreen : Screen("profile", "Profile")
}