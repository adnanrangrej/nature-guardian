package com.github.adnanrangrej.natureguardian.ui.screens.profile.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun EditProfileScreenImage(
    modifier: Modifier = Modifier,
    image: String,
    contentDescription: String = "Profile Image",
    onImageEditClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.clickable { onImageEditClick() }
    ) {
        NatureGuardianImages(
            url = image,
            modifier = Modifier.fillMaxSize(),
            placeholder = R.drawable.ic_profile_placeholder,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Edit",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditProfileScreenImagePreview() {
    NatureGuardianTheme {
        EditProfileScreenImage(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape),
            image = ""
        )
    }
}