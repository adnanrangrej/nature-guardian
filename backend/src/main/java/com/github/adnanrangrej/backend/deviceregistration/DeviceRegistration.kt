package com.github.adnanrangrej.backend.deviceregistration

import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.CreatePlatformEndpointRequest
import aws.sdk.kotlin.services.sns.model.SubscribeRequest
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.github.adnanrangrej.backend.deviceregistration.model.TokenRequest
import com.github.adnanrangrej.backend.deviceregistration.model.TokenResponse
import com.github.adnanrangrej.backend.getPlatformApplicationArn
import com.github.adnanrangrej.backend.getRegion
import com.github.adnanrangrej.backend.getSnsTopicArn
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class DeviceRegistration : RequestHandler<Any, TokenResponse> {

    private val platformApplicationArn = getPlatformApplicationArn()

    private val snsTopicArn = getSnsTopicArn()

    private val region = getRegion()

    // Gson Object to parse input.
    private val gson = Gson()

    override fun handleRequest(input: Any, context: Context): TokenResponse {

        return runBlocking {
            // logger to debug
            val logger = context.logger
            logger.log("Received input: $input")

            // Convert input to json string
            val jsonStr = gson.toJson(input)

            val tokenRequest = try {
                gson.fromJson(jsonStr, TokenRequest::class.java)
            } catch (e: Exception) {
                context.logger.log("Error during deserialization: ${e.message}\n")
                return@runBlocking TokenResponse(success = false, message = "Invalid input format")
            }

            val token = tokenRequest.token
            logger.log("Extracted token: '$token'\n")

            // Return if token is empty
            if (token.isEmpty()) {
                return@runBlocking TokenResponse(success = false, message = "Token is empty")
            }

            SnsClient { region = this@DeviceRegistration.region }.use { sns ->
                try {

                    // create device endpoint arn (identity of device) using device token
                    val createPlatformEndpointResponse = sns.createPlatformEndpoint(
                        CreatePlatformEndpointRequest {
                            platformApplicationArn = this@DeviceRegistration.platformApplicationArn
                            this.token = token
                        }
                    )
                    val endpointArn = createPlatformEndpointResponse.endpointArn
                    logger.log("Created platform endpoint: $endpointArn\n")

                    // subscribe the device to topic using device endpoint arn.
                    val subscribeResponse = sns.subscribe(
                        SubscribeRequest {
                            topicArn = snsTopicArn
                            protocol = "application"
                            endpoint = endpointArn
                        }
                    )
                    val subscriptionArn = subscribeResponse.subscriptionArn
                    logger.log("Subscribed endpoint to topic: $subscriptionArn\n")

                    // Return success response including the ARNs.
                    return@runBlocking TokenResponse(
                        success = true,
                        message = "Token registered and endpoint created successfully.",
                        endpointArn = endpointArn,
                        subscriptionArn = subscriptionArn
                    )
                } catch (e: Exception) {
                    logger.log("Error registering token: ${e.message}\n")
                    return@runBlocking TokenResponse(
                        success = false,
                        message = "Error registering token: ${e.message}"
                    )
                }
            }
        }
    }
}