package com.github.adnanrangrej.natureguardian.domain.repository

import android.graphics.Bitmap
import com.github.adnanrangrej.natureguardian.domain.model.notification.DefaultPayload
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenRequest
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenResponse

interface NotificationRepository {

    fun showNotification(title: String, body: String, largeIcon: Bitmap?, timestamp: String)

    suspend fun registerDevice(tokenRequest: TokenRequest): TokenResponse

    suspend fun loadBitmapFromUrl(url: String): Bitmap?

    fun parseNotificationData(jsonString: String): DefaultPayload?

}