package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.error_loading_image),
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }

    }
}