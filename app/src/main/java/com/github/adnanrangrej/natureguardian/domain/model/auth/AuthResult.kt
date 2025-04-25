package com.github.adnanrangrej.natureguardian.domain.model.auth

import com.github.adnanrangrej.natureguardian.domain.model.profile.User

sealed interface AuthResult {
    data class Success(val user: User) : AuthResult
    data class Error(val message: String) : AuthResult
    object Loading : AuthResult
}