package com.github.adnanrangrej.natureguardian.data.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import com.github.adnanrangrej.natureguardian.NatureGuardianActivity
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.data.remote.api.notification.BackendApiService
import com.github.adnanrangrej.natureguardian.domain.model.notification.DefaultPayload
import com.github.adnanrangrej.natureguardian.domain.model.notification.OuterPayload
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenRequest
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenResponse
import com.github.adnanrangrej.natureguardian.domain.repository.NotificationRepository
import com.google.gson.Gson

class NotificationRepositoryImpl(
    private val context: Context,
    private val channelId: String,
    private val channelName: String,
    private val channelDescription: String,
    private val apiService: BackendApiService
) : NotificationRepository {

    override fun showNotification(
        title: String,
        body: String,
        largeIcon: Bitmap?,
        timestamp: String
    ) {
        val notificationId = System.currentTimeMillis().toInt()
        val pendingIntent = createPendingIntent(timestamp)
        // Create the Notification
        val notification = createNotification(
            title,
            body,
            pendingIntent,
            channelId,
            R.color.brand_color,
            largeIcon
        )

        // Create notification channel for Android O and above
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel(channelId, channelName, channelDescription)
            notificationManager?.createNotificationChannel(channel)
        }
        Log.d("NotificationRepository", "Notification created")

        notificationManager?.notify(notificationId, notification.build())
        Log.d("NotificationRepository", "Displaying Notification")
    }

    override suspend fun registerDevice(tokenRequest: TokenRequest): TokenResponse {
        try {
            return apiService.registerDevice(tokenRequest)
        } catch (e: Exception) {
            Log.e("NotificationRepository", "Error registering device: ${e.message}")
            throw e
        }
    }

    override suspend fun loadBitmapFromUrl(url: String): Bitmap? {
        val loader = ImageLoader.Builder(context).build()
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = loader.execute(request)
        return if (result is SuccessResult) {
            result.image.toBitmap()
        } else {
            null
        }
    }

    override fun parseNotificationData(jsonString: String): DefaultPayload? {
        return try {
            val outerPayload = Gson().fromJson(jsonString, OuterPayload::class.java)
            Gson().fromJson(outerPayload.default, DefaultPayload::class.java)
        } catch (e: Exception) {
            Log.e("NotificationRepository", "Error parsing JSON: ${e.message}")
            throw e
        }

    }

    private fun createNotification(
        title: String,
        body: String,
        pendingIntent: PendingIntent,
        channelId: String,
        @ColorRes brandColor: Int,
        largeIconBitmap: Bitmap? = null
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(largeIconBitmap)
            .setContentTitle(title)
            .setColor(context.getColor(brandColor))
            .setContentText(body)
            .setColorized(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(largeIconBitmap)
                    .bigLargeIcon(largeIconBitmap)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String = ""
    ): NotificationChannel {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = channelDescription
        return channel
    }

    private fun createPendingIntent(timestamp: String): PendingIntent {
        val uri = "natureguardian://news/$timestamp".toUri()
        val intent = Intent(context, NatureGuardianActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data = uri
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent
    }
}