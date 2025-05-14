package com.github.adnanrangrej.natureguardian


object Utils {

    fun getBackendBaseUrl(): String {
        return BuildConfig.BACKEND_BASE_URL
    }

    fun getCloudinaryCloudName(): String {
        return BuildConfig.CLOUDINARY_CLOUD_NAME
    }

    fun getCloudinaryBackendUrl(): String {
        return BuildConfig.CLOUDINARY_BACKEND_URL
    }

    fun getCloudinaryBackendUrlApiKey(): String {
        return BuildConfig.CLOUDINARY_BACKEND_URL_API_KEY
    }
}

