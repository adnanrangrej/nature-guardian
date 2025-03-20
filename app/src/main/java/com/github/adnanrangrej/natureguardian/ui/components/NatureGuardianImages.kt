package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun NatureGuardianImages(
    url: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(url)
            .crossfade(true)
            .build()
    )
    val state = painter.state.collectAsState()


    when (state.value) {
        AsyncImagePainter.State.Empty -> {
            Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.empty_image),
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }

        is AsyncImagePainter.State.Error -> {
            Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.error_loading_image),
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }

        is AsyncImagePainter.State.Loading -> {
            Box(modifier = modifier.shimmerEffect())
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.success_image),
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun NatureGuardianImagesPreview() {
    NatureGuardianTheme {
        NatureGuardianImages(url = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
    }
}