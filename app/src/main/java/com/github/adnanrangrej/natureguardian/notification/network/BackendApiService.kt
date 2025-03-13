package com.github.adnanrangrej.natureguardian.notification.network

import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApiService {
    @POST("register-device")
    suspend fun registerDevice(@Body tokenRequest: TokenRequest): TokenResponse
}