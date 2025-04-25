package com.github.adnanrangrej.natureguardian.notification

import android.util.Log
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenRequest
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.GetCurrentUserUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.notification.LoadBitmapFromUrlUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.notification.ParseNotificationDataUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.notification.RegisterDeviceUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.notification.ShowNotificationUseCase
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
    lateinit var registerDeviceUseCase: RegisterDeviceUseCase

    @Inject
    lateinit var loadBitmapFromUrlUseCase: LoadBitmapFromUrlUseCase

    @Inject
    lateinit var parseNotificationDataUseCase: ParseNotificationDataUseCase

    @Inject
    lateinit var showNotificationUseCase: ShowNotificationUseCase

    @Inject
    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NatureGuardianFCMService", "New token: $token")
        val tokenRequest = TokenRequest(token = token)

        // send token to backend to register the device with sns
        serviceScope.launch {
            try {
                val response = registerDeviceUseCase(tokenRequest)
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

        if (getCurrentUserUseCase() == null) {
            Log.d("NatureGuardianFCMService", "User is not logged in")
            return
        }

        // Json string in the default field
        val outerJsonString = remoteMessage.data["default"]
        if (outerJsonString != null) {
            try {
                val defaultPayload = parseNotificationDataUseCase(outerJsonString)
                defaultPayload?.let { payload ->
                    Log.d("NatureGuardianFCMService", "Parsed Title: ${payload.title}")
                    Log.d("NatureGuardianFCMService", "Parsed Body: ${payload.body}")
                    Log.d("NatureGuardianFCMService", "Parsed Timestamp: ${payload.publishedAt}")
                    Log.d("NatureGuardianFCMService", "Parsed Image URL: ${payload.imgUrl}")

                    // Load Image and Show a notification
                    serviceScope.launch {
                        // Load the image from the URL
                        val bitmap = loadBitmapFromUrlUseCase(payload.imgUrl)
                        if (bitmap != null) {
                            Log.d("NatureGuardianFCMService", "Image Loaded Successfully!")
                        } else {
                            Log.e("NatureGuardianFCMService", "Image Loading Failed!")
                        }

                        // Show notification
                        showNotificationUseCase(
                            payload.title,
                            payload.body,
                            bitmap,
                            payload.publishedAt
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