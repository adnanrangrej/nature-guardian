package com.github.adnanrangrej.natureguardian.ui.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme
import kotlinx.coroutines.delay

@Composable
fun NatureGuardianSplashScreen(
    navigateToHomeScreen: () -> Unit = {},
    navigateToLoginScreen: () -> Unit = {},
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    var isMinTimePassed by remember { mutableStateOf(false) }
    val isPrepopulated = viewModel.isPrepopulated.collectAsState()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()


    LaunchedEffect(true) {
        println("Launched Effect - Min Time Delay Start")
        delay(2000)
        isMinTimePassed = true
        println("Launched Effect - Min Time Delay End")
    }

    LaunchedEffect(isMinTimePassed, isPrepopulated.value) {
        println("Launched Effect - Navigation Check: isDataReady=$isPrepopulated, isMinTimePassed=$isMinTimePassed")
        if (isPrepopulated.value && isMinTimePassed) {
            println("Conditions met. Navigating now.")
            if (isLoggedIn.value) {
                navigateToHomeScreen()
            } else {
                navigateToLoginScreen()
            }

        } else {
            println("Waiting for conditions: DataReady=$isPrepopulated, MinTimePassed=$isMinTimePassed")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App Icon",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.app_tagline),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (!isPrepopulated.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NatureGuardianSplashScreenPreview() {
    NatureGuardianTheme {
        NatureGuardianSplashScreen()
    }
}