package com.github.adnanrangrej.natureguardian.domain.usecase.notification

import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenRequest
import com.github.adnanrangrej.natureguardian.domain.model.notification.TokenResponse
import com.github.adnanrangrej.natureguardian.domain.repository.NotificationRepository
import javax.inject.Inject

class RegisterDeviceUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(tokenRequest: TokenRequest): TokenResponse =
        notificationRepository.registerDevice(tokenRequest)
}