package com.github.adnanrangrej.natureguardian

import io.github.cdimascio.dotenv.Dotenv

fun getBackendBaseUrl(): String {
    return Dotenv.load()["BACKEND_BASE_URL"] ?: throw Exception("Backend api base url is missing.")
}