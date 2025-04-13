package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.BottomNavItem
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianScreen

@Composable
fun NatureGuardianBottomNavBar(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val currentRoute = backStackEntry?.destination?.route


    val bottomNavItems = listOf(
        BottomNavItem(
            natureGuardianScreen = NatureGuardianScreen.SpeciesList,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            label = "Species"
        ),
        BottomNavItem(
            natureGuardianScreen = NatureGuardianScreen.NewsList,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
            label = "News"
        ),
        BottomNavItem(
            natureGuardianScreen = NatureGuardianScreen.Profile,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            label = "Profile"
        ),
    )

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = item.natureGuardianScreen.route == currentRoute,
                onClick = {
                    if (item.natureGuardianScreen.route != currentRoute) {
                        navController.navigate(item.natureGuardianScreen.route)
                    }
                },
                icon = {
                    if (item.natureGuardianScreen.route == currentRoute) {
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = item.label
                        )
                    } else {
                        Icon(
                            imageVector = item.unselectedIcon,
                            contentDescription = item.label
                        )
                    }
                }
            )

        }
    }
}