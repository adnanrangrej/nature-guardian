package com.github.adnanrangrej.natureguardian.notification.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import com.github.adnanrangrej.natureguardian.MainActivity
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.model.TokenResponse
import com.github.adnanrangrej.natureguardian.notification.network.BackendApiService

class NotificationRepositoryImpl(
    private val context: Context,
    private val channelId: String,
    private val channelName: String,
    private val channelDescription: String,
    private val apiService: BackendApiService
) : NotificationRepository {

    override fun showNotification(title: String, body: String, largeIcon: Bitmap?) {
        val notificationId = System.currentTimeMillis().toInt()
        val pendingIntent = createPendingIntent()
        // Create the Notification
        val notification = createNotification(
            title,
            body,
            pendingIntent,
            largeIcon,
            channelId,
            R.mipmap.ic_launcher,
            R.color.brand_color
        )

        // Create notification channel for Android O and above
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel(channelId, channelName, channelDescription)
            notificationManager?.createNotificationChannel(channel)
        }
        Log.d("NatureGuardianFCMService", "Notification created")

        notificationManager?.notify(notificationId, notification.build())
        Log.d("NatureGuardianFCMService", "Displaying Notification")
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

    private fun createNotification(
        title: String,
        body: String,
        pendingIntent: PendingIntent,
        largeIcon: Bitmap?,
        channelId: String,
        @DrawableRes smallIcon: Int,
        @ColorRes brandColor: Int
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setColor(context.getColor(brandColor))
            .setContentText(body)
            .setColorized(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(largeIcon)
                    .bigLargeIcon(largeIcon)
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

    private fun createPendingIntent(): PendingIntent {
        // Create intent to launch our app when notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
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