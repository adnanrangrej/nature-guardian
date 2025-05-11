package com.github.adnanrangrej.natureguardian.ui.screens.profile.editprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.profile.User
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun EditProfileBody(
    modifier: Modifier = Modifier,
    uiState: EditProfileUiState,
    onImageEditClick: () -> Unit = {},
    onValueChange: (User) -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        EditProfileScreenImage(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            image = uiState.userDetail.profileImageUrl ?: "",
            contentDescription = "Profile Image",
            onImageEditClick = onImageEditClick
        )

        Spacer(Modifier.height(32.dp))

        Text(text = uiState.userDetail.name)

        Spacer(Modifier.height(16.dp))

        EditProfileBottom(
            name = uiState.userDetail.name,
            bio = uiState.userDetail.bio ?: "",
            onNameChange = { onValueChange(uiState.userDetail.copy(name = it)) },
            onBioChange = { onValueChange(uiState.userDetail.copy(bio = it)) },
            isLoading = uiState.isSavingProfile
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onSaveClick,
            enabled = uiState.isEntryValid && !uiState.isSavingProfile,
            modifier = Modifier.size(width = 250.dp, height = 50.dp)
        ) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditProfileBodyPreview() {
    NatureGuardianTheme {
        EditProfileBody(
            uiState = EditProfileUiState()
        )
    }
}