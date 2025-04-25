package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianScreen
import com.github.adnanrangrej.natureguardian.ui.screens.auth.login.LoginScreen
import com.github.adnanrangrej.natureguardian.ui.screens.auth.signup.SignUpScreen

fun NavController.navigateToLoginRoute() {
    navigate(NatureGuardianScreen.Login.route) {
        popUpTo(NatureGuardianScreen.Login.route) {
            inclusive = true
        }
    }
}

fun NavController.navigateToSignUpRoute() {
    navigate(NatureGuardianScreen.Signup.route) {
        popUpTo(NatureGuardianScreen.Signup.route) {
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
        startDestination = NatureGuardianScreen.Login.route,
        route = route
    ) {
        // Auth Screens
        loginRoute(
            navigateToSignUp = navController::navigateToSignUpRoute,
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
    composable(route = NatureGuardianScreen.Login.route) {
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
    composable(route = NatureGuardianScreen.Signup.route) {
        SignUpScreen(
            navigateToLogin = navigateToSignIn,
            navigateToHome = navigateToHome
        )
    }
}