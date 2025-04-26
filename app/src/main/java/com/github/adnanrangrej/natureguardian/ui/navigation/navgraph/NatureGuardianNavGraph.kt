package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianBottomNavBar
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianTopAppBar
import com.github.adnanrangrej.natureguardian.ui.navigation.Helper

@Composable
fun NatureGuardianNavGraph(
    navController: NavHostController,
) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val currentRoute = backStackEntry?.destination?.route

    // Find the current screen based on the route
    val currentScreen = Helper.findScreenByRoute(currentRoute)

    Scaffold(
        topBar = {
            if (currentScreen?.showTopBar == true) {
                NatureGuardianTopAppBar(
                    canNavigate = currentScreen.canNavigateBack,
                    navigateUp = navController::navigateUp
                )
            }
        },
        bottomBar = {
            if (currentScreen?.showBottomBar == true) {
                NatureGuardianBottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        RootNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}