package com.github.adnanrangrej.natureguardian.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NatureGuardianFCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NatureGuardianFCMService", "New token: $token")

        // send token to backend to register the device with sns
        sendTokenToBackend(token)
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

    private fun sendTokenToBackend(token: String) {

    }
}