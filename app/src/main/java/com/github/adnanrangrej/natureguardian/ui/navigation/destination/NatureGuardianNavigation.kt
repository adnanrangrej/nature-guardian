package com.github.adnanrangrej.natureguardian.ui.navigation.destination

interface NatureGuardianNavigation {
    val route: String
    val title: String
    val showTopBar: Boolean
    val showBottomBar: Boolean
    val canNavigateBack: Boolean
}
