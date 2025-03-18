package com.github.adnanrangrej.natureguardian.notification

import android.util.Log
import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NatureGuardianFCMService : FirebaseMessagingService() {

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NatureGuardianFCMService", "New token: $token")
        val tokenRequest = TokenRequest(token = token)

        // send token to backend to register the device with sns
        serviceScope.launch {
            try {
                val response = notificationRepository.registerDevice(tokenRequest)
                Log.d("NatureGuardianFCMService", "Response: ${response.message}")
            } catch (e: Exception) {
                Log.e("NatureGuardianFCMService", "Error registering device: ${e.message}")
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // full data payload
        Log.d("NatureGuardianFCMService", "message received: ${remoteMessage.data}")

        // Json string in the default field
        val outerJsonString = remoteMessage.data["default"]
        if (outerJsonString != null) {
            try {
                val defaultPayload = notificationRepository.parseNotificationData(outerJsonString)
                defaultPayload?.let { payload ->
                    Log.d("NatureGuardianFCMService", "Parsed Title: ${payload.title}")
                    Log.d("NatureGuardianFCMService", "Parsed Body: ${payload.body}")
                    Log.d("NatureGuardianFCMService", "Parsed Timestamp: ${payload.publishedAt}")
                    Log.d("NatureGuardianFCMService", "Parsed Image URL: ${payload.imgUrl}")

                    // Load Image and Show a notification
                    serviceScope.launch {
                        // Load the image from the URL
                        val bitmap = notificationRepository.loadBitmapFromUrl(payload.imgUrl)
                        if (bitmap != null) {
                            Log.d("NatureGuardianFCMService", "Image Loaded Successfully!")
                        } else {
                            Log.e("NatureGuardianFCMService", "Image Loading Failed!")
                        }

                        // Show notification
                        notificationRepository.showNotification(
                            payload.title,
                            payload.body,
                            bitmap
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("NatureGuardianFCMService", "Error parsing JSON with Gson: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d("NatureGuardianFCMService", "Service destroyed")
    }
}