package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianGraph
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.splash.SplashNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.auth.authNavGraph
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.mainNavGraphGraph
import com.github.adnanrangrej.natureguardian.ui.screens.splashscreen.NatureGuardianSplashScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SplashNavigation.Splash.route,
        route = NatureGuardianGraph.ROOT,
        modifier = modifier
    ) {
        composable(route = SplashNavigation.Splash.route) {
            NatureGuardianSplashScreen(
                navigateToHomeScreen = {
                    navController.navigate(NatureGuardianGraph.MAIN) {
                        popUpTo(SplashNavigation.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToLoginScreen = {
                    navController.navigate(NatureGuardianGraph.AUTHENTICATION) {
                        popUpTo(SplashNavigation.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        mainNavGraphGraph(
            navHostController = navController,
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
    }
}