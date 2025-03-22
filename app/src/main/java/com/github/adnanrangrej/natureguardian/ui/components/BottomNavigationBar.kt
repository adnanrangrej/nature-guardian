package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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


    val items = listOf(
        BottomNavItem(
            natureGuardianScreen = NatureGuardianScreen.SpeciesList,
            icon = Icons.Default.Home,
            label = "Species"
        ),
        BottomNavItem(
            natureGuardianScreen = NatureGuardianScreen.NewsList,
            icon = Icons.AutoMirrored.Default.List,
            label = "News"
        ),
        BottomNavItem(
            natureGuardianScreen = NatureGuardianScreen.Profile,
            icon = Icons.Default.Person,
            label = "Profile"
        ),
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.natureGuardianScreen.route == currentRoute,
                onClick = {
                    if (item.natureGuardianScreen.route != currentRoute){
                        navController.navigate(item.natureGuardianScreen.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                }
            )

        }
    }
}