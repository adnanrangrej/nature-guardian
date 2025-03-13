package com.github.adnanrangrej.natureguardian.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.adnanrangrej.natureguardian.MainActivity
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.notification.model.DefaultPayload
import com.github.adnanrangrej.natureguardian.notification.model.OuterPayload
import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.network.BackendApiService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class NatureGuardianFCMService : FirebaseMessagingService() {

    @Inject
    @Named("backend")
    lateinit var backendApiService: BackendApiService

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NatureGuardianFCMService", "New token: $token")
        val tokenRequest = TokenRequest(token = token)

        // send token to backend to register the device with sns
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = backendApiService.registerDevice(tokenRequest)
                Log.d("NatureGuardianFCMService", "Response message: ${response.message}")
            } catch (e: Exception) {
                Log.e("NatureGuardianFCMService", "Failed to register device: ${e.message}")
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // full data payload
        Log.d("FCMService", "message received: ${remoteMessage.data}")

        val outerJsonString = remoteMessage.data["default"]
        Log.d("NatureGuardianFCMService", "Outer string: $outerJsonString")

        if (outerJsonString != null) {
            try {
                val gson = Gson()
                // Parse the outer payload
                val outerPayload = gson.fromJson(outerJsonString, OuterPayload::class.java)
                // Parse the DefaultPayload from the 'default' field of the outer payload
                val defaultPayload = gson.fromJson(outerPayload.default, DefaultPayload::class.java)

                Log.d("FCMService", "Parsed Title: ${defaultPayload.title}")
                Log.d("FCMService", "Parsed Body: ${defaultPayload.body}")
                Log.d("FCMService", "Parsed URL: ${defaultPayload.url}")

                 // Show a notification
                showNotification(defaultPayload.title, defaultPayload.body)
            } catch (e: Exception) {
                Log.e("FCMService", "Error parsing JSON with Gson: ${e.message}")
            }
        }
    }


    private fun showNotification(title: String, body: String) {

        val channelId = "conservation_news_channel"
        val notificationId = System.currentTimeMillis().toInt()

        // Create intent to launch our app when notification is tapped

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create the Notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Create notification channel for Android O and above
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "Conservation News",
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationManager.createNotificationChannel(channel)
        }
        Log.d("NatureGuardianFCMService", "Notification created")


        notificationManager.notify(notificationId, notification.build())
        Log.d("NatureGuardianFCMService", "Displaying Notification")

    }
}