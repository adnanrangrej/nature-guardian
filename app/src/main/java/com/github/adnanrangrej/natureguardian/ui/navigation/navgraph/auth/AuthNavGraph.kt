package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.auth.AuthNavigation
import com.github.adnanrangrej.natureguardian.ui.screens.auth.login.LoginScreen
import com.github.adnanrangrej.natureguardian.ui.screens.auth.signup.SignUpScreen

fun NavController.navigateToLoginRoute() {
    navigate(AuthNavigation.Login.route) {
        popUpTo(AuthNavigation.Login.route) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    route: String,
    onLoginSuccess: () -> Unit,
) {
    navigation(
        startDestination = AuthNavigation.Login.route,
        route = route
    ) {
        // Auth Screens
        loginRoute(
            navigateToSignUp = { navController.navigate(AuthNavigation.Register.route) },
            navigateToHome = onLoginSuccess
        )
        signUpRoute(
            navigateToSignIn = navController::navigateToLoginRoute,
            navigateToHome = onLoginSuccess
        )
    }
}

private fun NavGraphBuilder.loginRoute(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = AuthNavigation.Login.route) {
        LoginScreen(
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome
        )
    }
}

private fun NavGraphBuilder.signUpRoute(
    navigateToSignIn: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = AuthNavigation.Register.route) {
        SignUpScreen(
            navigateToLogin = navigateToSignIn,
            navigateToHome = navigateToHome
        )
    }
}