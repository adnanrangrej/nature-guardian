package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.BottomNavItem
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianGraph
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.MainGraph

@Composable
fun NatureGuardianBottomNavBar(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val currentDestination = backStackEntry?.destination


    val bottomNavItems = listOf(
        BottomNavItem(
            graphRoute = MainGraph.SPECIES,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            label = "Species"
        ),
        BottomNavItem(
            graphRoute = MainGraph.LOCATION,
            selectedIcon = Icons.Filled.Map,
            unselectedIcon = Icons.Outlined.Map,
            label = "Species Distribution"
        ),
        BottomNavItem(
            graphRoute = MainGraph.NEWS,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
            label = "News"
        ),
        BottomNavItem(
            graphRoute = MainGraph.PROFILE,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            label = "Profile"
        ),
    )

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.parent?.route == item.graphRoute,
                onClick = {
                    navController.navigate(item.graphRoute) {
                        popUpTo(NatureGuardianGraph.MAIN) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.parent?.route == item.graphRoute) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}