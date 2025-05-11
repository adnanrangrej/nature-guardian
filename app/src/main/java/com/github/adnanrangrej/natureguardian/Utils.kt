package com.github.adnanrangrej.natureguardian

import io.github.cdimascio.dotenv.dotenv


object Utils {
    val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    fun getBackendBaseUrl(): String {
        return dotenv["BACKEND_BASE_URL"] ?: throw Exception("Backend base URL is missing")
    }

    fun getCloudinaryCloudName(): String {
        return dotenv["CLOUDINARY_CLOUD_NAME"]
            ?: throw Exception("Cloudinary cloud name is missing")
    }

    fun getCloudinaryApiKey(): String {
        return dotenv["CLOUDINARY_API_KEY"] ?: throw Exception("Cloudinary API key is missing")
    }

    fun getCloudinaryApiSecret(): String {
        return dotenv["CLOUDINARY_API_SECRET"]
            ?: throw Exception("Cloudinary API secret is missing")
    }
}

