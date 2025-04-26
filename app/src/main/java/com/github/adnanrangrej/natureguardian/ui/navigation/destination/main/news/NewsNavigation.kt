package com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.news

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.news.NewsScreenRoute.NEWS_DETAIL_SCREEN_ROUTE
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.news.NewsScreenRoute.NEWS_LIST_SCREEN_ROUTE

sealed class NewsNavigation : NatureGuardianNavigation {
    object NewsList : NewsNavigation() {
        override val route: String
            get() = NEWS_LIST_SCREEN_ROUTE
        override val title: String
            get() = "News"
        override val showTopBar: Boolean
            get() = true
        override val showBottomBar: Boolean
            get() = true
        override val canNavigateBack: Boolean
            get() = false
    }

    object NewsDetail : NewsNavigation() {
        override val route: String
            get() = NEWS_DETAIL_SCREEN_ROUTE
        override val title: String
            get() = "News Detail"
        override val showTopBar: Boolean
            get() = true
        override val showBottomBar: Boolean
            get() = false
        override val canNavigateBack: Boolean
            get() = true

        fun createRoute(timestamp: String) = "news_screen/$timestamp"
    }


}