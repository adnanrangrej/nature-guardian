package com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.species

import com.github.adnanrangrej.natureguardian.ui.navigation.destination.NatureGuardianNavigation
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.species.SpeciesScreenRoute.SPECIES_DETAIL_SCREEN
import com.github.adnanrangrej.natureguardian.ui.navigation.destination.main.species.SpeciesScreenRoute.SPECIES_LIST_SCREEN

sealed class SpeciesNavigation : NatureGuardianNavigation {
    object SpeciesList : SpeciesNavigation() {
        override val route: String
            get() = SPECIES_LIST_SCREEN
        override val title: String
            get() = "Species List"
        override val showTopBar: Boolean
            get() = true
        override val showBottomBar: Boolean
            get() = true
        override val canNavigateBack: Boolean
            get() = false
    }

    object SpeciesDetail : SpeciesNavigation() {
        override val route: String
            get() = SPECIES_DETAIL_SCREEN
        override val title: String
            get() = "Species Detail"
        override val showTopBar: Boolean
            get() = false
        override val showBottomBar: Boolean
            get() = false
        override val canNavigateBack: Boolean
            get() = true

        fun createRoute(internalTaxonId: Long) = "species_screen/$internalTaxonId"
    }

}