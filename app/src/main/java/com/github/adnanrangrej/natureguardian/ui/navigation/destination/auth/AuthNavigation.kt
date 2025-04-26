package com.github.adnanrangrej.natureguardian.ui.navigation.destination.auth

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.auth.AuthScreenRoute.LOGIN_SCREEN_ROUTE
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.auth.AuthScreenRoute.SIGNUP_SCREEN_ROUTE

sealed class AuthNavigation : NatureGuardianNavigation {
    object Login : AuthNavigation() {
        override val route: String
            get() = LOGIN_SCREEN_ROUTE
        override val title: String
            get() = "Sign In"
        override val showTopBar: Boolean
            get() = false
        override val showBottomBar: Boolean
            get() = false
        override val canNavigateBack: Boolean
            get() = false
    }

    object Register : AuthNavigation() {
        override val route: String
            get() = SIGNUP_SCREEN_ROUTE
        override val title: String
            get() = "Register"
        override val showTopBar: Boolean
            get() = false
        override val showBottomBar: Boolean
            get() = false
        override val canNavigateBack: Boolean
            get() = false
    }
}