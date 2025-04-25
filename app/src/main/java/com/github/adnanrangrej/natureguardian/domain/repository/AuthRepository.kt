package com.github.adnanrangrej.natureguardian.domain.repository

import com.github.adnanrangrej.natureguardian.domain.model.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<AuthResult>
    fun signUp(email: String, password: String, name: String): Flow<AuthResult>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}