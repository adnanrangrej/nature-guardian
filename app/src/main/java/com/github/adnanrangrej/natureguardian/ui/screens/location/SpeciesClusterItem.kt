package com.github.adnanrangrej.natureguardian.ui.screens.location

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class SpeciesClusterItem(
    val speciesId: Long,
    val speciesName: String,
    val latitude: Double,
    val longitude: Double
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getTitle(): String? {
        return speciesName
    }

    override fun getSnippet(): String? {
        return "Tap to view details"
    }

    override fun getZIndex(): Float? {
        return 0f
    }
}