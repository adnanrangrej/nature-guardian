package com.github.adnanrangrej.natureguardian.ui.navigation

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.auth.AuthNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.location.LocationNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.news.NewsNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.profile.ProfileNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.species.SpeciesNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.splash.SplashNavigation

object Helper {
    val allScreen = listOf<NatureGuardianNavigation>(
        // Splash
        SplashNavigation.Splash,

        // Auth
        AuthNavigation.Login,
        AuthNavigation.Register,

        // News
        NewsNavigation.NewsList,
        NewsNavigation.NewsDetail,

        // Profile
        ProfileNavigation.Profile,
        ProfileNavigation.EditProfile,

        // Species
        SpeciesNavigation.SpeciesList,
        SpeciesNavigation.SpeciesDetail,

        // Location
        LocationNavigation.LocationMap
    )

    fun findScreenByRoute(currentRoute: String?): NatureGuardianNavigation? {
        return allScreen.find { screen ->
            val baseRoute = screen.route.substringBefore("/{")
            if (screen.route.contains("/{")) {
                currentRoute?.startsWith("$baseRoute/") == true
            } else {
                currentRoute == screen.route
            }
        }
    }
}