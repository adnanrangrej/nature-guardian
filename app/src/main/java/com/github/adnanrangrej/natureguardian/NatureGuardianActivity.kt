package com.github.adnanrangrej.natureguardian

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.github.adnanrangrej.natureguardian.data.local.preferences.PrefsHelper
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class NatureGuardianActivity : ComponentActivity() {
    @Inject
    lateinit var prefsHelper: PrefsHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        var isPrepopulated = false
        splashScreen.setKeepOnScreenCondition { !isPrepopulated }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    prefsHelper.checkAndPrepopulateDatabase()
                } catch (e: Exception) {
                    Log.e("NatureGuardianActivity", "Error during prepopulation", e)
                }
                isPrepopulated = prefsHelper.checkIfPrepopulatedDatabase()
                Log.d("Splash", "Setting isPrepopulated = $isPrepopulated")
            }
        }

        // Request permission for notifications on launch
        requestNotificationPermission()

        setContent {
            NatureGuardianTheme {
                NatureGuardianApp()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }

    companion object {
        private const val REQUEST_NOTIFICATION_PERMISSION = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Notification", "Permission granted ✅")
            } else {
                Log.e("Notification", "Permission denied ❌")
                Toast.makeText(
                    this,
                    "Enable notifications for important updates!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

