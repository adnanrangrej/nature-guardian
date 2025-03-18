package com.github.adnanrangrej.natureguardian.notification.repository

import android.graphics.Bitmap
import com.github.adnanrangrej.natureguardian.notification.model.DefaultPayload
import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.model.TokenResponse

interface NotificationRepository {

    fun showNotification(title: String, body: String, largeIcon: Bitmap?)

    suspend fun registerDevice(tokenRequest: TokenRequest): TokenResponse

    suspend fun loadBitmapFromUrl(url: String): Bitmap?

    fun parseNotificationData(jsonString: String): DefaultPayload?

}