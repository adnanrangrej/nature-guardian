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
}

