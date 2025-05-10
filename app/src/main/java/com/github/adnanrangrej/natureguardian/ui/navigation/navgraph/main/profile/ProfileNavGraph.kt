package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.profile.ProfileNavigation
import com.github.adnanrangrej.natureguardian.ui.screens.profile.EditProfileScreen
import com.github.adnanrangrej.natureguardian.ui.screens.profile.showprofile.ProfileScreen

fun NavController.navigateToProfile() {
    navigate(ProfileNavigation.Profile.route) {
        popUpTo(ProfileNavigation.Profile.route) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    route: String,
    onLogout: () -> Unit
) {
    navigation(
        startDestination = ProfileNavigation.Profile.route,
        route = route
    ) {
        profileScreenRoute(
            onLogout = onLogout,
            navigateToEditProfile = { navController.navigate(ProfileNavigation.EditProfile.route) }
        )

        editProfileScreenRoute(navigateToProfile = navController::navigateToProfile)
    }
}

fun NavGraphBuilder.profileScreenRoute(onLogout: () -> Unit, navigateToEditProfile: () -> Unit) {
    composable(route = ProfileNavigation.Profile.route) {
        ProfileScreen(navigateToLogin = onLogout, navigateToEditProfile = navigateToEditProfile)
    }
}

fun NavGraphBuilder.editProfileScreenRoute(navigateToProfile: () -> Unit) {
    composable(
        route = ProfileNavigation.EditProfile.route
    ) {
        EditProfileScreen(onSaveChangesClick = navigateToProfile)
    }
}