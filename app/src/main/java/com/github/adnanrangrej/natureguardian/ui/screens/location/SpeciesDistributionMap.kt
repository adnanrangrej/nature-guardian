package com.github.adnanrangrej.natureguardian.ui.screens.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun SpeciesDistributionMap(
    modifier: Modifier = Modifier,
    clusterItems: List<SpeciesClusterItem>,
    onClusterItemClick: (speciesId: Long) -> Unit
) {
    val initialCameraPosition = LatLng(20.5937, 78.9629)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialCameraPosition, 5f)
    }
    var selectedItem by remember { mutableStateOf<SpeciesClusterItem?>(null) }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            mapToolbarEnabled = false
        )
    ) {
        Clustering(
            items = clusterItems,
            onClusterClick = { cluster ->
                // Zoom in on the cluster to show individual markers
                cameraPositionState.move(
                    CameraUpdateFactory.newLatLngZoom(
                        cluster.position,
                        cameraPositionState.position.zoom + 2
                    )
                )
                false
            },
            onClusterItemClick = { clusterItem ->
                println("Marker clicked: ${clusterItem.speciesName}")
                selectedItem = clusterItem
                println("selected item: ${selectedItem?.speciesName}")
                // Move camera to the marker's position
                cameraPositionState.move(
                    CameraUpdateFactory.newLatLngZoom(clusterItem.position, 10f)
                )
                false
            },
            onClusterItemInfoWindowClick = { item ->
                onClusterItemClick(item.speciesId)
            }
        )
    }
}

@Composable
fun CustomInfoWindow(
    item: SpeciesClusterItem,
    onInfoIconClick: () -> Unit
) {
    Box {
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.speciesName,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = onInfoIconClick) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "View species details",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}