package com.github.adnanrangrej.natureguardian.ui.navigation.destination.splash

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.splash.SplashScreenRoute.SPLASH_SCREEN_ROUTE

sealed class SplashNavigation : NatureGuardianNavigation {
    object Splash : SplashNavigation() {
        override val route: String
            get() = SPLASH_SCREEN_ROUTE
        override val title: String
            get() = "Splash Screen"
        override val showTopBar: Boolean
            get() = false
        override val showBottomBar: Boolean
            get() = false
        override val canNavigateBack: Boolean
            get() = false
    }
}