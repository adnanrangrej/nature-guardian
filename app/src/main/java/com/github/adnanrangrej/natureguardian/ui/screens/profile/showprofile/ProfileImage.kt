package com.github.adnanrangrej.natureguardian.ui.screens.profile.showprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    image: String,
    contentDescription: String = "Profile Image"
) {
    NatureGuardianImages(
        url = image,
        modifier = modifier,
        placeholder = R.drawable.ic_profile_placeholder,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}