package com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.profile

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.profile.ProfileScreenRoute.EDIT_PROFILE_SCREEN_ROUTE
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.profile.ProfileScreenRoute.PROFILE_SCREEN_ROUTE

sealed class ProfileNavigation : NatureGuardianNavigation {
    object Profile : ProfileNavigation() {
        override val route: String
            get() = PROFILE_SCREEN_ROUTE
        override val title: String
            get() = "Profile"
        override val showTopBar: Boolean
            get() = true
        override val showBottomBar: Boolean
            get() = true
        override val canNavigateBack: Boolean
            get() = false
    }

    object EditProfile : ProfileNavigation() {
        override val route: String
            get() = EDIT_PROFILE_SCREEN_ROUTE
        override val title: String
            get() = "Edit Profile"
        override val showTopBar: Boolean
            get() = true
        override val showBottomBar: Boolean
            get() = false
        override val canNavigateBack: Boolean
            get() = true
    }
}