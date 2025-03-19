package com.github.adnanrangrej.natureguardian.domain.model.notification

// This response we will receive from api
data class TokenResponse(
    val success: Boolean,
    val message: String,
    val endpointArn: String? = null,
    val subscriptionArn: String? = null
)