package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.main.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.news.NewsNavigation
import com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail.NewsDetailScreen
import com.github.adnanrangrej.natureguardian.ui.screens.news.newslist.NewsListScreen

fun NavGraphBuilder.newsNavGraph(
    navHostController: NavHostController,
    route: String
) {
    navigation(
        startDestination = NewsNavigation.NewsList.route,
        route = route,
    ) {
        newsListRoute { newsId ->
            navHostController.navigate(NewsNavigation.NewsDetail.createRoute(newsId))
        }
        newsDetailRoute()

    }
}

private fun NavGraphBuilder.newsListRoute(
    navigateToNewsDetail: (String) -> Unit
) {
    composable(route = NewsNavigation.NewsList.route) {
        NewsListScreen(navigateToNewsDetail = navigateToNewsDetail)
    }
}

private fun NavGraphBuilder.newsDetailRoute() {
    composable(
        route = NewsNavigation.NewsDetail.route,
        arguments = listOf(
            navArgument("timestamp") {
                type = NavType.StringType
            }),
        deepLinks = listOf(navDeepLink {
            uriPattern = "natureguardian://news_screen/{timestamp}"
        })
    ) {
        NewsDetailScreen()
    }
}