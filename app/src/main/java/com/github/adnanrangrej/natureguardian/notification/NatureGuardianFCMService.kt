package com.github.adnanrangrej.natureguardian.notification

import android.util.Log
import com.github.adnanrangrej.natureguardian.notification.model.TokenRequest
import com.github.adnanrangrej.natureguardian.notification.network.BackendApiService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("NatureGuardianFCMService", "message received: ${message.data}")
        Log.d("NatureGuardianFCMService", "Notification: ${message.notification}")

        // Extract the message data
        val title = message.notification?.title ?: "New Conservation News"
        val body = message.notification?.body ?: "Tap to read the latest update."

        // Show notification to user
        showNotification(title, body)

    }

    private fun showNotification(title: String, body: String) {


    }
}