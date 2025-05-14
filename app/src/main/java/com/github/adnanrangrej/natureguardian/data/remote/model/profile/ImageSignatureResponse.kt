package com.github.adnanrangrej.natureguardian.data.remote.model.profile

import com.google.gson.annotations.SerializedName

data class ImageSignatureResponse(
    @SerializedName("api_key")
    val apiKey: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("timestamp")
    val timestamp: Int
)