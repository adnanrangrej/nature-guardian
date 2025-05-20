# DeviceRegistration AWS Lambda Function

The `DeviceRegistration` Lambda function is a key component of the **NatureGuardian** backend, enabling push notifications by registering device tokens with AWS Simple Notification Service (SNS). It creates an SNS platform endpoint for a device and subscribes it to a predefined SNS topic, allowing the application to broadcast notifications to all registered devices.

> ⚠️ **Educational Demo**  
> This Lambda function is part of the NatureGuardian project, intended for educational and demonstration purposes. Follow the setup instructions carefully to configure your own AWS resources.

---

## Table of Contents

- [Purpose](#purpose)
- [Functionality](#functionality)
- [Input and Output Formats](#input-and-output-formats)
- [Environment Variables](#environment-variables)
- [IAM Permissions](#iam-permissions)
- [Prerequisites](#prerequisites)
- [Deployment](#deployment)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Purpose

The `DeviceRegistration` Lambda function registers a device's push notification token (e.g., Firebase Cloud Messaging (FCM) token from an Android or iOS device) with AWS SNS. It creates a platform endpoint for the device and subscribes it to an SNS topic, enabling the **NatureGuardian** app to send push notifications to all registered devices by publishing messages to the topic.

This function is typically invoked via an API Gateway endpoint by the client application when a device registers for notifications.

---

## Functionality

The Lambda function performs the following steps:

1. **Receives Input**: Accepts a JSON payload containing the device’s push notification token.
2. **Parses Input**: Extracts the `token` field from the JSON input.
3. **Validates Token**: Ensures the token is non-empty, returning an error if invalid.
4. **Creates SNS Platform Endpoint**: Uses the AWS SDK for Kotlin to call the `createPlatformEndpoint` SNS API, registering the device token with the specified SNS Platform Application and generating a unique `EndpointArn`.
5. **Subscribes Endpoint to SNS Topic**: Calls the `subscribe` SNS API to link the `EndpointArn` to a predefined SNS topic, returning a `SubscriptionArn`.
6. **Returns Response**:
   - On success: Returns a JSON response with the `EndpointArn` and `SubscriptionArn`.
   - On failure: Returns a JSON error response with a descriptive message (e.g., invalid input or AWS SDK errors).

---

## Input and Output Formats

### Input Format

The function expects a JSON object with the following structure:

```json
{
  "token": "YOUR_DEVICE_PUSH_NOTIFICATION_TOKEN"
}
```

- **`token`** (String, Required): The push notification token from the client device (e.g., FCM token for Android or APNS token for iOS).

### Output Format

#### Success Response

```json
{
  "success": true,
  "message": "Token registered and endpoint created successfully.",
  "endpointArn": "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:endpoint/YOUR_PLATFORM_APPLICATION_NAME/ENDPOINT_ID",
  "subscriptionArn": "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:YOUR_SNS_TOPIC_NAME:SUBSCRIPTION_ID"
}
```

#### Error Response

```json
{
  "success": false,
  "message": "Error message (e.g., 'Token is empty', 'Invalid input format', 'Error registering token: Specific AWS SDK error')"
}
```

---

## Environment Variables

The function requires the following environment variables, configured in the AWS Lambda console or deployment configuration:

| Variable                   | Description                                                  | Example Value                                              |
|----------------------------|--------------------------------------------------------------|------------------------------------------------------------|
| `PLATFORM_APPLICATION_ARN` | ARN of the SNS Platform Application (e.g., for FCM or APNS). | `arn:aws:sns:us-east-1:123456789012:app/GCM/YourAppName`   |
| `SNS_TOPIC_ARN`            | ARN of the SNS Topic for notifications.                      | `arn:aws:sns:us-east-1:123456789012:YourNotificationTopic` |
| `AWS_REGION`               | AWS region where SNS resources are located.                  | `us-east-1`                                                |

These are accessed in the Kotlin code using utility functions like `getPlatformApplicationArn()`, `getSnsTopicArn()`, and `getRegion()`.

> **Security Note**: Never hardcode these values in the source code. Use environment variables and secure them with AWS IAM policies.

---

## IAM Permissions

The Lambda function’s execution role must include the following IAM permissions to interact with AWS SNS and CloudWatch Logs:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "sns:CreatePlatformEndpoint",
                "sns:Subscribe"
            ],
            "Resource": [
                "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:app/YOUR_PLATFORM/YOUR_APP_NAME",
                "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:YOUR_SNS_TOPIC_NAME"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "logs:CreateLogGroup",
                "logs:CreateLogStream",
                "logs:PutLogEvents"
            ],
            "Resource": "arn:aws:logs:YOUR_REGION:YOUR_ACCOUNT_ID:log-group:/aws/lambda/YOUR_LAMBDA_FUNCTION_NAME:*"
        }
    ]
}
```

### Notes:
- Replace `YOUR_REGION`, `YOUR_ACCOUNT_ID`, `YOUR_PLATFORM/YOUR_APP_NAME`, `YOUR_SNS_TOPIC_NAME`, and `YOUR_LAMBDA_FUNCTION_NAME` with your actual values.
- Scope the `Resource` ARNs to your specific SNS Platform Application and Topic for enhanced security.
- The CloudWatch Logs permissions allow the Lambda to log execution details for debugging.

---

## Prerequisites

Before deploying the function, ensure you have:

1. **AWS Account**: With access to SNS, Lambda, IAM, and CloudWatch.
2. **SNS Platform Application**: Configured in AWS SNS for your platform (e.g., FCM for Android).
   - Obtain the `PLATFORM_APPLICATION_ARN` from the SNS console.
3. **SNS Topic**: Created in AWS SNS for broadcasting notifications.
   - Obtain the `SNS_TOPIC_ARN` from the SNS console.
4. **Kotlin Development Environment**:
   - Gradle with ShadowJar plugin for building the JAR.
   - AWS SDK for Kotlin (`aws-sdk-kotlin`).
5. **AWS CLI or SDK**: For deployment (optional, if not using the AWS Management Console).
6. **API Gateway (Optional)**: If exposing the Lambda as an HTTP endpoint.

---

## Deployment

Follow these steps to deploy the `DeviceRegistration` Lambda function:

1. **Build the Lambda**:
   - This project uses the ShadowJar Gradle plugin to create a fat JAR including all dependencies.
   - Run the following command in the project directory:
     ```bash
     ./gradlew :backend:shadowJar
     ```
   - The output JAR will be in `build/libs/`.

2. **Create the Lambda Function**:
   - In the AWS Management Console, navigate to Lambda and create a new function.
   - Choose **Java 11 (Corretto)** as the runtime (compatible with Kotlin).
   - Upload the generated JAR file from `build/libs/`.

3. **Configure the Handler**:
   - Set the handler to: `com.github.adnanrangrej.backend.deviceregistration.DeviceRegistration::handleRequest`

4. **Set Environment Variables**:
   - In the Lambda configuration, add the following environment variables:
     - `PLATFORM_APPLICATION_ARN`
     - `SNS_TOPIC_ARN`
     - `AWS_REGION`
   - Example:
     ```plaintext
     PLATFORM_APPLICATION_ARN=arn:aws:sns:us-east-1:123456789012:app/GCM/NatureGuardian
     SNS_TOPIC_ARN=arn:aws:sns:us-east-1:123456789012:NatureGuardianNotifications
     AWS_REGION=us-east-1
     ```

5. **Assign IAM Role**:
   - Create or use an existing IAM role with the permissions listed in the [IAM Permissions](#iam-permissions) section.
   - Attach the role to the Lambda function.

6. **Configure Triggers**:
   - To expose the Lambda as an HTTP endpoint, create an API Gateway trigger:
     - In the Lambda console, add an API Gateway trigger.
     - Choose **REST API**, configure a new or existing API, and set the endpoint type (e.g., Regional).
     - Enable CORS if the API will be called from a web or mobile client.
     - Deploy the API to a stage (e.g., `prod`) to obtain the endpoint URL.
   - Note the API Gateway endpoint URL (e.g., `https://your-api-id.execute-api.us-east-1.amazonaws.com/prod`) for use in the client application.

7. **Test the Function**:
   - Use the Lambda console’s test feature or send a POST request to the API Gateway endpoint with a sample JSON payload:
     ```json
     {
       "token": "sample-fcm-token-12345"
     }
     ```

---

## Usage

1. **Client-Side Integration**:
   - In the **NatureGuardian** app, obtain the device’s FCM token using Firebase Cloud Messaging.
   - Send a POST request to the API Gateway endpoint with the JSON payload (see [Input Format](#input-and-output-formats)).
   - Example using `curl`:
     ```bash
     curl -X POST https://your-api-id.execute-api.us-east-1.amazonaws.com/prod -H "Content-Type: application/json" -d '{"token":"sample-fcm-token-12345"}'
     ```

2. **Response Handling**:
   - Parse the response to confirm successful registration (`success: true`) or handle errors (`success: false`).
   - Store the `endpointArn` and `subscriptionArn` if needed for further SNS operations (e.g., unsubscribing).

---

## Troubleshooting

- **Error: "Token is empty"**:
  - Ensure the `token` field in the JSON input is non-empty and correctly formatted.
- **Error: "Invalid input format"**:
  - Verify the input JSON structure matches the expected format.
- **SNS Errors**:
  - Check the `PLATFORM_APPLICATION_ARN` and `SNS_TOPIC_ARN` environment variables for correctness.
  - Ensure the Lambda’s IAM role has the required SNS permissions.
- **API Gateway 403/500 Errors**:
  - Confirm CORS is enabled if calling from a client application.
  - Verify the API Gateway stage is deployed and the endpoint URL is correct.
- **Logs**:
  - Check CloudWatch Logs (`/aws/lambda/YOUR_LAMBDA_FUNCTION_NAME`) for detailed error messages.

---

## Contributing

Contributions to improve this Lambda function are welcome! To contribute:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m "Add YourFeature"`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

Please include:
- A clear description of changes.
- Updated documentation in this README if new features or environment variables are added.
- Tests for new functionality (if applicable).

---

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE) file in the root of the **NatureGuardian** repository for details.

MIT © 2025 Md Adnan
