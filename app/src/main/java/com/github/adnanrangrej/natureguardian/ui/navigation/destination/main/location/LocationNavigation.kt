package com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.location

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.location.LocationScreenRoute.LOCATION_MAP_SCREEN_ROUTE

sealed class LocationNavigation : NatureGuardianNavigation {
    object LocationMap : LocationNavigation() {
        override val route: String
            get() = LOCATION_MAP_SCREEN_ROUTE
        override val title: String
            get() = "Species Distribution"
        override val showTopBar: Boolean
            get() = true
        override val showBottomBar: Boolean
            get() = true
        override val canNavigateBack: Boolean
            get() = false

    }
}