package com.github.adnanrangrej.natureguardian.domain.model.profile

sealed interface ProfileResult {
    data class Success(val user: User) : ProfileResult
    data class Error(val error: String) : ProfileResult
    object Loading : ProfileResult
}