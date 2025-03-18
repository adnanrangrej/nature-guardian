package com.github.adnanrangrej.natureguardian.ui.navigation.navgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.Screen

@Composable
fun NatureGuardianNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.SpeciesScreen.route,
        modifier = modifier
    ) {

        composable(route = Screen.SpeciesScreen.route) {
            Text(text = "Species Screen")
        }

        composable(route = Screen.NewsScreen.route) {
            Text(text = "News Screen")
        }

        composable(route = Screen.ProfileScreen.route) {
            Text(text = "Profile Screen")
        }


    }

}