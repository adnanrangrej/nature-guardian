package com.github.adnanrangrej.natureguardian.domain.usecase.notification

import android.graphics.Bitmap
import com.github.adnanrangrej.natureguardian.domain.repository.NotificationRepository
import javax.inject.Inject

class ShowNotificationUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(title: String, body: String, largeIcon: Bitmap?) =
        notificationRepository.showNotification(title, body, largeIcon)
}