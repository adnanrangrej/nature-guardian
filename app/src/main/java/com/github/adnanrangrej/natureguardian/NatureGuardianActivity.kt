package com.github.adnanrangrej.natureguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NatureGuardianActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NatureGuardianTheme {
                NatureGuardianApp()
            }
        }
    }
}

