package com.github.adnanrangrej.natureguardian.domain.model.notification

// Represents the outer SNS payload structure.
data class OuterPayload(
    val default: String,
    val GCM: String
)


