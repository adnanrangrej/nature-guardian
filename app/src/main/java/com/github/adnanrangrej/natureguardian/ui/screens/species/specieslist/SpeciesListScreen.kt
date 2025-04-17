package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.adnanrangrej.natureguardian.NatureGuardianActivity
import com.github.adnanrangrej.natureguardian.ui.components.NotificationPermissionDialog

@Composable
fun SpeciesListScreen(
    viewModel: SpeciesListViewModel = hiltViewModel(),
    navigateToSpeciesDetail: (Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val showDialog by viewModel.showNotificationDialog.collectAsState()
    val context = LocalContext.current

    SpeciesListBody(
        uiState = uiState.value,
        onSpeciesClick = navigateToSpeciesDetail,
        modifier = Modifier.fillMaxSize(),
        getCommonName = viewModel::getCommonName,
        getImageUrl = viewModel::getMainUrl,
    )

    if (showDialog) {
        NotificationPermissionDialog(
            onDismiss = {
                viewModel.onNotificationDialogHandled()
            },
            onConfirm = {
                viewModel.onNotificationDialogHandled()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {

                        ActivityCompat.requestPermissions(
                            context as NatureGuardianActivity,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            1001
                        )
                    }
                }
            },
        )
    }
}