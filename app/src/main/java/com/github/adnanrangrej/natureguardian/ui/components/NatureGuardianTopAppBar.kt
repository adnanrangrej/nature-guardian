package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.adnanrangrej.natureguardian.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NatureGuardianTopAppBar(
    modifier: Modifier = Modifier,
    canNavigate: Boolean,
    navigateUp: () -> Unit = {}

) {
    TopAppBar(
        title = {
            Column {
                Text(text = stringResource(R.string.app_name))
                Text(
                    text = stringResource(R.string.app_tagline),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigate) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}