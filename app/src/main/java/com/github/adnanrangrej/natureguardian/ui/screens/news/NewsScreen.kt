package com.github.adnanrangrej.natureguardian.ui.screens.news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianTopAppBar

@Composable
fun NewsScreen(
    title: String,
    viewModel: NewsScreenViewModel = hiltViewModel(),
    navigateToNewsDetail: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            NatureGuardianTopAppBar(
                title = title,
                canNavigate = false
            )
        }
    ) { innerPadding ->
        NewsBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            uiState = uiState.value,
            onItemClick = navigateToNewsDetail,
            retryAction = viewModel::loadInitialNews,
            fetchMoreNews = viewModel::loadNextPage
        )
    }
}