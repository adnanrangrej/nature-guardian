package com.github.adnanrangrej.backend.deviceregistration.model

// This is the response DeviceRegistration Lambda will give(Output).
data class TokenResponse(
    val success: Boolean,
    val message: String,
    val endpointArn: String? = null,
    val subscriptionArn: String? = null
)
