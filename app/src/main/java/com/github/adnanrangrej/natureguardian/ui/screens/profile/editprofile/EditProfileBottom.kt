package com.github.adnanrangrej.natureguardian.ui.screens.profile.editprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun EditProfileBottom(
    modifier: Modifier = Modifier,
    name: String = "",
    bio: String = "",
    onNameChange: (String) -> Unit = {},
    onBioChange: (String) -> Unit = {},
    isLoading: Boolean = false
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            OutlinedTextField(
                value = bio,
                onValueChange = onBioChange,
                label = { Text(text = "Bio") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                maxLines = 10,
                enabled = !isLoading
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditProfileBottomPreview() {
    NatureGuardianTheme {
        EditProfileBottom()
    }
}