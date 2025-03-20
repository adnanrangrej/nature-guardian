package com.github.adnanrangrej.natureguardian

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.NatureGuardianNavGraph

@Composable
fun NatureGuardianApp() {

    val navController = rememberNavController()

    NatureGuardianNavGraph(navController = navController)
}