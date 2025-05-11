package com.github.adnanrangrej.natureguardian.ui.screens.profile.showprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.profile.ProfileResult

@Composable
fun ProfileScreenBody(
    modifier: Modifier = Modifier,
    uiState: ProfileResult,
    onEditProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    when (uiState) {
        is ProfileResult.Loading -> {
            Box(modifier) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is ProfileResult.Success -> {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                ProfileImage(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape),
                    image = uiState.user.profileImageUrl.orEmpty(),
                    contentDescription = "Profile Image"
                )

                Text(
                    text = uiState.user.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = uiState.user.email,
                    style = MaterialTheme.typography.bodyMedium
                )

                ProfileScreenBottom(
                    bio = uiState.user.bio.orEmpty(),
                    createdAt = uiState.user.createdAt
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onEditProfileClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Edit")
                    }

                    Button(
                        onClick = onLogoutClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Logout")
                    }
                }
            }
        }

        is ProfileResult.Error -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error)
            }
        }
    }
}
