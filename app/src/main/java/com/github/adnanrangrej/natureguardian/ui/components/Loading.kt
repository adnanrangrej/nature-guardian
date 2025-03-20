package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp)
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    NatureGuardianTheme {
        Loading(Modifier.fillMaxSize())
    }

}