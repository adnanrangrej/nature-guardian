package com.github.adnanrangrej.cloudinarysignaturegenerator

import com.cloudinary.Cloudinary
import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import com.google.gson.Gson
import java.io.IOException

class CloudinarySignatureGenerator : HttpFunction {

    private val gson = Gson()
    override fun service(request: HttpRequest, response: HttpResponse) {

        // Handle OPTIONS preflight request for CORS
        if ("OPTIONS" == request.method) {
            response.setStatusCode(204) // No Content
            return
        }

        // Ensure it's a POST request for actual signature generation
        if ("POST" != request.method) {
            response.setStatusCode(405) // Method Not Allowed
            response.writer.write(gson.toJson(mapOf("error" to "Method not allowed. Please use POST.")))
            return
        }

        try {
            // Retrieve Cloudinary credentials from environment variables
            val cloudName = System.getenv("CLOUDINARY_CLOUD_NAME")
            val apiKey = System.getenv("CLOUDINARY_API_KEY")
            val apiSecret = System.getenv("CLOUDINARY_API_SECRET")

            if (cloudName.isNullOrBlank() || apiKey.isNullOrBlank() || apiSecret.isNullOrBlank()) {
                System.err.println("Cloudinary environment variables are not properly configured.")
                response.setStatusCode(500)
                response.writer.write(gson.toJson(mapOf("error" to "Server configuration error.")))
                return
            }

            // Initialize Cloudinary instance
            val cloudinaryConfig = HashMap<String, String>()
            cloudinaryConfig["cloud_name"] = cloudName
            cloudinaryConfig["api_key"] = apiKey
            cloudinaryConfig["api_secret"] = apiSecret
            cloudinaryConfig["secure"] = "true" // Ensure secure URLs
            val cloudinary = Cloudinary(cloudinaryConfig)

            val timestamp = (System.currentTimeMillis() / 1000L) // Unix timestamp in seconds

            val paramsToSign = mutableMapOf<String, Any>()
            paramsToSign["timestamp"] = timestamp
            paramsToSign["folder"] = "profile_images"

            // Generate the signature
            val signature = cloudinary.apiSignRequest(paramsToSign, apiSecret)

            // Prepare the response
            val responsePayload = mapOf(
                "signature" to signature,
                "timestamp" to timestamp,
                "api_key" to apiKey // Send API key back for convenience
            )

            response.setStatusCode(200)
            response.setContentType("application/json")
            response.writer.write(gson.toJson(responsePayload))

        } catch (e: IOException) {
            System.err.println("IOException: ${e.message}")
            response.setStatusCode(500)
            response.writer.write(gson.toJson(mapOf("error" to "Error reading request: ${e.message}")))
        } catch (e: Exception) {
            System.err.println("Error generating signature: ${e.message}")
            e.printStackTrace() // Log the full stack trace to Cloud Logging
            response.setStatusCode(500)
            response.writer.write(gson.toJson(mapOf("error" to "Failed to generate signature: ${e.message}")))
        }
    }
}