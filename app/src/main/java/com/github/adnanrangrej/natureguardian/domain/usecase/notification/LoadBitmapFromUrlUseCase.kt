package com.github.adnanrangrej.natureguardian.domain.usecase.notification

import com.github.adnanrangrej.natureguardian.domain.repository.NotificationRepository
import javax.inject.Inject

class LoadBitmapFromUrlUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(url: String) = notificationRepository.loadBitmapFromUrl(url)
}