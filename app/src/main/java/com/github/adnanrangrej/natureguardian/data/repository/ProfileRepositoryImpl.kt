package com.github.adnanrangrej.natureguardian.data.repository

import com.github.adnanrangrej.natureguardian.domain.model.profile.ProfileResult
import com.github.adnanrangrej.natureguardian.domain.model.profile.User
import com.github.adnanrangrej.natureguardian.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions.merge
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_COLLECTION = "users"

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ProfileRepository {
    override fun getUserProfile(): Flow<ProfileResult> {
        return callbackFlow {
            trySend(ProfileResult.Loading)

            // Get the current user's ID
            val userId = auth.currentUser?.uid
            if (userId == null) {
                // User not authenticated
                trySend(ProfileResult.Error("User not authenticated"))
                return@callbackFlow
            }

            // Fetch the user's profile from Firestore
            val listener = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        // Error occurred while fetching the profile
                        trySend(ProfileResult.Error(error.message ?: "Unknown error"))
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        // User profile found
                        val user = snapshot.toObject(User::class.java)

                        if (user != null) {
                            // Emit the user profile
                            trySend(ProfileResult.Success(user))
                        } else {
                            // User profile is null
                            trySend(ProfileResult.Error("User profile is null"))
                        }
                    } else {
                        // User profile not found
                        trySend(ProfileResult.Error("User profile not found"))
                    }
                }
            awaitClose {
                // Remove the snapshot listener when the flow is cancelled
                listener.remove()
            }
        }
    }

    override fun updateUserProfile(user: User): Flow<ProfileResult> {
        return flow {
            emit(ProfileResult.Loading)

            try {
                // Get current user
                val firebaseUser = auth.currentUser ?: throw Exception("User not authenticated")

                // Create new user profile
                val newUser = user.copy(uid = firebaseUser.uid)

                // Document ref
                val documentRef = firestore.collection(USERS_COLLECTION).document(firebaseUser.uid)
                documentRef.set(newUser, merge()).await()

                // Emit success
                emit(ProfileResult.Success(newUser))

            } catch (e: Exception) {
                emit(ProfileResult.Error(e.message ?: "Unknown error"))
            }
        }
    }

}