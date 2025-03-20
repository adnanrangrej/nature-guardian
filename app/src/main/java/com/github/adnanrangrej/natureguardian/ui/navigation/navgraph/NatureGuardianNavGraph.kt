package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianBottomNavBar
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianTopAppBar
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianScreen
import com.github.adnanrangrej.natureguardian.ui.screens.news.NewsScreen
import com.github.adnanrangrej.natureguardian.ui.screens.profile.ProfileScreen
import com.github.adnanrangrej.natureguardian.ui.screens.species.SpeciesScreen

@Composable
fun NatureGuardianNavGraph(
    navController: NavHostController,
) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val currentRoute = backStackEntry?.destination?.route

    // Find the current screen based on the route
    val currentScreen = when {
        currentRoute?.startsWith("species/") == true -> NatureGuardianScreen.SpeciesDetail
        currentRoute?.startsWith("news/") == true -> NatureGuardianScreen.NewsDetail
        currentRoute?.startsWith("map/") == true -> NatureGuardianScreen.Map
        currentRoute == "species" -> NatureGuardianScreen.SpeciesList
        currentRoute == "news" -> NatureGuardianScreen.NewsList
        currentRoute == "profile" -> NatureGuardianScreen.Profile
        else -> null
    }


    Scaffold(
        topBar = {
            if (currentScreen?.showTopBar == true) {
                NatureGuardianTopAppBar(
                    title = currentScreen.title,
                    canNavigate = navController.previousBackStackEntry != null
                )
            }

        },
        bottomBar = {
            if (currentScreen?.showBottomBar == true) {
                NatureGuardianBottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NatureGuardianScreen.SpeciesList.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(route = NatureGuardianScreen.SpeciesList.route) {
                SpeciesScreen()
            }

            composable(route = NatureGuardianScreen.NewsList.route) {
                NewsScreen(navigateToNewsDetail = { })
            }

            composable(route = NatureGuardianScreen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}