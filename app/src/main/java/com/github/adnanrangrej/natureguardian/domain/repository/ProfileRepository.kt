package com.github.adnanrangrej.natureguardian.domain.repository

import com.github.adnanrangrej.natureguardian.domain.model.profile.ProfileResult
import com.github.adnanrangrej.natureguardian.domain.model.profile.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {
    fun getUserProfile(): Flow<ProfileResult>
    fun updateUserProfile(user: User): Flow<ProfileResult>
    suspend fun uploadProfileImage(file: File): String?
}