package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianScreen
import com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail.NewsDetailScreen
import com.github.adnanrangrej.natureguardian.ui.screens.news.newslist.NewsListScreen
import com.github.adnanrangrej.natureguardian.ui.screens.profile.ProfileScreen
import com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.SpeciesDetailScreen
import com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist.SpeciesListScreen

private const val TransitionDuration = 300

fun NavController.navigateToSpeciesDetail(speciesId: Long) {
    navigate(NatureGuardianScreen.SpeciesDetail.createRoute(speciesId))
}

fun NavController.navigateToNewsDetail(timestamp: String) {
    navigate(NatureGuardianScreen.NewsDetail.createRoute(timestamp))
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController, route: String, onLogout: () -> Unit
) {
    navigation(
        startDestination = NatureGuardianScreen.SpeciesList.route, route = route
    ) {
        // Main Nav Graph
        speciesListRoute(navigateToSpeciesDetail = navController::navigateToSpeciesDetail)

        speciesDetailRoute(onNavigateUp = navController::navigateUp)

        newsListRoute(navigateToNewsDetail = navController::navigateToNewsDetail)

        newsDetailRoute()

        profileRoute(navigateToLogin = onLogout)

    }
}

private fun NavGraphBuilder.speciesListRoute(
    navigateToSpeciesDetail: (Long) -> Unit
) {
    composable(route = NatureGuardianScreen.SpeciesList.route, exitTransition = {
        slideOutHorizontally(
            targetOffsetX = { -it }, animationSpec = tween(TransitionDuration)
        ) + fadeOut(animationSpec = tween(TransitionDuration / 2))
    }, popEnterTransition = {
        slideInHorizontally(
            initialOffsetX = { -it }, animationSpec = tween(TransitionDuration)
        ) + fadeIn(animationSpec = tween(TransitionDuration / 2))
    }) {
        SpeciesListScreen(navigateToSpeciesDetail = navigateToSpeciesDetail)
    }
}

private fun NavGraphBuilder.speciesDetailRoute(onNavigateUp: () -> Unit) {
    composable(
        route = NatureGuardianScreen.SpeciesDetail.route, arguments = listOf(
            navArgument("internal_taxon_id") {
                type = NavType.LongType
            }), enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(TransitionDuration)
            ) + fadeIn(animationSpec = tween(TransitionDuration / 2))
        }, popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(TransitionDuration)
            ) + fadeOut(animationSpec = tween(TransitionDuration / 2))
        }) {
        SpeciesDetailScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

private fun NavGraphBuilder.newsListRoute(
    navigateToNewsDetail: (String) -> Unit
) {
    composable(route = NatureGuardianScreen.NewsList.route, exitTransition = {
        slideOutHorizontally(
            targetOffsetX = { -it }, animationSpec = tween(TransitionDuration)
        ) + fadeOut(animationSpec = tween(TransitionDuration / 2))
    }, popEnterTransition = {
        slideInHorizontally(
            initialOffsetX = { -it }, animationSpec = tween(TransitionDuration)
        ) + fadeIn(animationSpec = tween(TransitionDuration / 2))
    }) {
        NewsListScreen(
            navigateToNewsDetail = navigateToNewsDetail
        )
    }
}

private fun NavGraphBuilder.newsDetailRoute() {
    composable(
        route = NatureGuardianScreen.NewsDetail.route, arguments = listOf(
            navArgument("timestamp") {
                type = NavType.StringType
            }), deepLinks = listOf(navDeepLink {
            uriPattern = "natureguardian://news/{timestamp}"
        }),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TransitionDuration)
            ) +
                    fadeIn(animationSpec = tween(TransitionDuration / 2))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TransitionDuration)
            ) +
                    fadeOut(animationSpec = tween(TransitionDuration / 2))
        }
    ) {
        NewsDetailScreen()
    }
}

private fun NavGraphBuilder.profileRoute(navigateToLogin: () -> Unit) {
    composable(route = NatureGuardianScreen.Profile.route) {
        ProfileScreen(navigateToLogin = navigateToLogin)
    }
}