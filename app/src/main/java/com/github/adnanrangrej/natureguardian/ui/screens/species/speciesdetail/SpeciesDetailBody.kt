package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesDetailBody(
    modifier: Modifier = Modifier,
    uiState: SpeciesDetailUiState,
    getCommonName: (DetailedSpecies) -> String?,
    getImageUrl: (DetailedSpecies) -> String?,
    navigateUp: () -> Unit
) {
    when (uiState) {
        is SpeciesDetailUiState.Error -> {
            ErrorScreen(
                retryAction = {},
                modifier = modifier,
                errorMessage = R.string.db_error
            )
        }

        SpeciesDetailUiState.Loading -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is SpeciesDetailUiState.Success -> {
            SpeciesDetailCard(
                modifier = modifier,
                species = uiState.species,
                commonName = getCommonName(uiState.species),
                imageUrl = getImageUrl(uiState.species)
            )
        }
    }
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = navigateUp,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.statusBarsPadding()
    )
}