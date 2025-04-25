package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianGraph
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianScreen
import com.github.adnanrangrej.natureguardian.ui.screens.splashscreen.NatureGuardianSplashScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NatureGuardianScreen.SplashScreen.route,
        route = NatureGuardianGraph.ROOT,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        composable(route = NatureGuardianScreen.SplashScreen.route) {
            NatureGuardianSplashScreen(
                navigateToHomeScreen = {
                    navController.navigate(NatureGuardianGraph.MAIN) {
                        popUpTo(NatureGuardianScreen.SplashScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                navigateToLoginScreen = {
                    navController.navigate(NatureGuardianGraph.AUTHENTICATION) {
                        popUpTo(NatureGuardianScreen.SplashScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        authNavGraph(
            navController = navController,
            route = NatureGuardianGraph.AUTHENTICATION,
            onLoginSuccess = {
                navController.navigate(NatureGuardianGraph.MAIN) {
                    popUpTo(NatureGuardianGraph.AUTHENTICATION) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )

        mainNavGraph(
            navController = navController,
            route = NatureGuardianGraph.MAIN,
            onLogout = {
                navController.navigate(NatureGuardianGraph.AUTHENTICATION) {
                    popUpTo(NatureGuardianGraph.MAIN) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }
}