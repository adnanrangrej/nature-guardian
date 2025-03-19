package com.github.adnanrangrej.natureguardian.domain.usecase.notification

import com.github.adnanrangrej.natureguardian.domain.repository.NotificationRepository
import javax.inject.Inject

class ParseNotificationDataUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(jsonString: String) =
        notificationRepository.parseNotificationData(jsonString)
}