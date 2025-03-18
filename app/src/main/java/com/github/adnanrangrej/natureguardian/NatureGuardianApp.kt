package com.github.adnanrangrej.natureguardian

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianBottomNavBar
import com.github.adnanrangrej.natureguardian.ui.navigation.navgraph.NatureGuardianNavGraph

@Composable
fun NatureGuardianApp() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { NatureGuardianBottomNavBar(navController = navController) }
    ) { innerPadding ->
        NatureGuardianNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )

    }
}