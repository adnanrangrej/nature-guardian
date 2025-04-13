package com.github.adnanrangrej.natureguardian.ui.navigation.destination

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val natureGuardianScreen: NatureGuardianScreen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)
