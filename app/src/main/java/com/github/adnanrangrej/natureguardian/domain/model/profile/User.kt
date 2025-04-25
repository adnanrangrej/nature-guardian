package com.github.adnanrangrej.natureguardian.domain.model.profile

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val userType: String = "registered"
)