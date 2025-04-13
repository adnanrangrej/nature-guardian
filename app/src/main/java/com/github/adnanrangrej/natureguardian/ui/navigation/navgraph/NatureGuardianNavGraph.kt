package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianBottomNavBar
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianTopAppBar
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianScreen
import com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail.NewsDetailScreen
import com.github.adnanrangrej.natureguardian.ui.screens.news.newslist.NewsListScreen
import com.github.adnanrangrej.natureguardian.ui.screens.profile.ProfileScreen
import com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist.SpeciesListScreen

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
        currentRoute == NatureGuardianScreen.SpeciesList.route -> NatureGuardianScreen.SpeciesList
        currentRoute == NatureGuardianScreen.NewsList.route -> NatureGuardianScreen.NewsList
        currentRoute == NatureGuardianScreen.Profile.route -> NatureGuardianScreen.Profile
        else -> null
    }


    Scaffold(
        topBar = {
            if (currentScreen?.showTopBar == true) {
                NatureGuardianTopAppBar(
                    title = currentScreen.title,
                    canNavigate = currentScreen.canNavigateBack,
                    navigateUp = { navController.navigateUp() }
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
                SpeciesListScreen()
            }

            composable(route = NatureGuardianScreen.NewsList.route) {
                NewsListScreen(navigateToNewsDetail = { timestamp ->
                    navController.navigate(NatureGuardianScreen.NewsDetail.createRoute(timestamp))
                })
            }

            composable(
                route = NatureGuardianScreen.NewsDetail.route, arguments = listOf(
                    navArgument("timestamp") {
                        type = NavType.StringType
                    }
                ),
                deepLinks = listOf(navDeepLink { uriPattern = "natureguardian://news/{timestamp}" })
            ) {
                NewsDetailScreen()
            }

            composable(route = NatureGuardianScreen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}