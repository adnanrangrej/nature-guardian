package com.github.adnanrangrej.natureguardian.notification

import android.util.Log
import com.github.adnanrangrej.natureguardian.notification.model.DefaultPayload
import com.github.adnanrangrej.natureguardian.notification.model.OuterPayload
import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NatureGuardianFCMService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NatureGuardianFCMService", "New token: $token")
        val tokenRequest = TokenRequest(token = token)

        // send token to backend to register the device with sns
        CoroutineScope(Dispatchers.IO).launch {
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
                Log.d("FCMService", "Parsed Image URL: ${defaultPayload.imgUrl}")

                // Show a notification
                CoroutineScope(Dispatchers.IO).launch {

                    // Load the image from the URL
                    val bitmap = notificationRepository.loadBitmapFromUrl(defaultPayload.imgUrl)
                    Log.d("NatureGuardianFCMService", "Image Loaded Successfully!")

                    // Show notification
                    notificationRepository.showNotification(
                        defaultPayload.title,
                        defaultPayload.body,
                        bitmap
                    )

                }
            } catch (e: Exception) {
                Log.e("FCMService", "Error parsing JSON with Gson: ${e.message}")
            }
        }
    }


}