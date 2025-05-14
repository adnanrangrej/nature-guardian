package com.github.adnanrangrej.natureguardian.data.remote.api.profile

import com.github.adnanrangrej.natureguardian.Utils.getCloudinaryBackendUrlApiKey
import com.github.adnanrangrej.natureguardian.data.remote.model.profile.ImageSignatureResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface UploadImageApiService {
    @POST("v1/generate-signature")
    suspend fun generateSignature(@Header("x-api-key") apiKey: String = getCloudinaryBackendUrlApiKey()): ImageSignatureResponse
}