package com.github.adnanrangrej.natureguardian.ui.screens.profile.showprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ProfileScreenBottom(
    modifier: Modifier = Modifier,
    bio: String = "",
    createdAt: Long = 0L
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = bio,
                minLines = 3,
                maxLines = 5
            )
            HorizontalDivider()
            Text(
                text = "Joined ${getFormattedCreatedAt(createdAt)}"
            )
        }
    }
}

fun getFormattedCreatedAt(createdAt: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val date = Instant.ofEpochMilli(createdAt)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return formatter.format(date)
}