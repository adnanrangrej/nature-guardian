package com.github.adnanrangrej.natureguardian.data.remote.api.notification

import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenRequest
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApiService {
    @POST("register-device")
    suspend fun registerDevice(@Body tokenRequest: TokenRequest): TokenResponse
}