package com.github.adnanrangrej.natureguardian.data.repository

import com.github.adnanrangrej.natureguardian.domain.model.auth.AuthResult
import com.github.adnanrangrej.natureguardian.domain.model.profile.User
import com.github.adnanrangrej.natureguardian.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_COLLECTION = "users"

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override fun login(
        email: String,
        password: String
    ): Flow<AuthResult> {
        return flow {
            emit(AuthResult.Loading)
            try {
                // Sign in with email and password
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user
                    ?: throw IllegalStateException("User was null after successful login")

                // Fetch user details from Firestore
                val userDocument = firestore.collection(USERS_COLLECTION)
                    .document(firebaseUser.uid)
                    .get()
                    .await()
                if (userDocument.exists()) {
                    // Parse user profile data
                    val userData =
                        userDocument.toObject(User::class.java)?.copy(uid = firebaseUser.uid)
                    if (userData != null) {
                        // Login success
                        emit(AuthResult.Success(userData))
                    } else {
                        // Login failure - user profile not found
                        emit(AuthResult.Error("Failed to parse user profile data."))
                    }
                } else {
                    // Login failure - user profile not found
                    emit(AuthResult.Error("User profile not found in database"))
                }

            } catch (e: Exception) {
                // login failure
                val errorMessage = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Invalid email or password."
                    is FirebaseAuthUserCollisionException -> "This email is already registered."
                    is FirebaseAuthException -> {
                        when (e.errorCode) {
                            "ERROR_USER_DISABLED" -> "This user account has been disabled."
                            "ERROR_USER_NOT_FOUND" -> "No user found with this email."
                            "ERROR_WRONG_PASSWORD" -> "Incorrect password entered."
                            "ERROR_TOO_MANY_REQUESTS" -> "Too many login attempts. Try again later."
                            else -> "Authentication failed: ${e.localizedMessage}"
                        }
                    }

                    is IllegalStateException -> e.message ?: "Login failed unexpectedly."
                    else -> e.localizedMessage ?: "Unknown login error occurred."
                }
                emit(AuthResult.Error(errorMessage))
            }
        }
    }

    override fun signUp(
        email: String,
        password: String,
        name: String
    ): Flow<AuthResult> {
        return flow {
            emit(AuthResult.Loading)

            try {
                // Create user with email and password
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user
                    ?: throw IllegalStateException("User was null after successful signup")

                // Create User profile object
                val uid = firebaseUser.uid
                val user = User(uid = uid, name = name, email = email)

                // Save user to Firestore
                firestore.collection(USERS_COLLECTION)
                    .document(uid)
                    .set(user)
                    .await()

                // Signup success
                emit(AuthResult.Success(user))
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthUserCollisionException -> "This email address is already in use."
                    is FirebaseAuthInvalidCredentialsException -> "Invalid email format or weak password."
                    is FirebaseAuthException -> {
                        when (e.errorCode) {
                            "ERROR_WEAK_PASSWORD" -> "The password is too weak. Use a stronger password."
                            "ERROR_EMAIL_ALREADY_IN_USE" -> "An account already exists with this email."
                            "ERROR_OPERATION_NOT_ALLOWED" -> "Sign-up is currently disabled. Please try later."
                            else -> "Authentication failed: ${e.localizedMessage}"
                        }
                    }

                    is IllegalStateException -> e.message ?: "Signup failed unexpectedly."
                    else -> e.localizedMessage ?: "Unknown signup error occurred."
                }
                emit(AuthResult.Error(errorMessage))
            }
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

}