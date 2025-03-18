package com.github.adnanrangrej.natureguardian.notification.model

// Represents the notification data stored in the "default" field.
data class DefaultPayload(
    val title: String,
    val body: String,
    val imgUrl: String,
    val url: String? = null
)