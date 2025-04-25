package com.github.adnanrangrej.natureguardian.domain.usecase.auth

import com.github.adnanrangrej.natureguardian.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, password: String) = authRepository.login(email, password)
}