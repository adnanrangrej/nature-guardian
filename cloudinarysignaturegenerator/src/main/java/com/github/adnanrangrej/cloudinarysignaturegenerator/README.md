# CloudinarySignatureGenerator Google Cloud Function

The `CloudinarySignatureGenerator` is an HTTP-triggered Google Cloud Function that securely
generates signatures for client-side uploads to Cloudinary. This enables the **NatureGuardian** app
to allow users to upload files (e.g., profile images) directly to Cloudinary without exposing
sensitive API secrets or routing files through a backend server.

> ⚠️ **Educational Demo**  
> This function is part of the NatureGuardian project, intended for educational and demonstration
> purposes. Follow the setup instructions to configure your own Google Cloud and Cloudinary resources.

---

## Table of Contents

- [Purpose](#purpose)
- [Functionality](#functionality)
- [API Endpoint](#api-endpoint)
- [Environment Variables](#environment-variables)
- [IAM Permissions](#iam-permissions)
- [Prerequisites](#prerequisites)
- [Deployment](#deployment)
- [Exposing via API Gateway](#exposing-via-api-gateway)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Purpose

The `CloudinarySignatureGenerator` function generates secure signatures for direct client-side
uploads to Cloudinary. By providing a signature, timestamp, and API key, it allows the *
*NatureGuardian** app to upload files (e.g., user profile images) to Cloudinary securely, without
exposing the Cloudinary API secret to clients.

---

## Functionality

The function performs the following steps:

1. **CORS Preflight Handling**: Responds to `OPTIONS` requests with a `204 No Content` status to
   support browser CORS preflight checks.
2. **Request Method Validation**: Accepts only `POST` requests for signature generation, returning a
   `405 Method Not Allowed` error for other methods.
3. **Environment Variable Retrieval**: Fetches Cloudinary credentials (`CLOUDINARY_CLOUD_NAME`,
   `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`) from environment variables.
4. **Cloudinary Initialization**: Configures the Cloudinary SDK with the retrieved credentials.
5. **Parameter Preparation**:
    - Generates a current Unix timestamp (in seconds).
    - Sets parameters for signing, including the timestamp and a predefined folder (
      `profile_images`) for uploads.
6. **Signature Generation**: Uses the Cloudinary SDK’s `apiSignRequest` method to generate a secure
   signature.
7. **Response Construction**:
    - On success: Returns a `200 OK` JSON response with the signature, timestamp, and Cloudinary API
      key.
    - On failure: Returns an error response (e.g., `500 Internal Server Error`) with a descriptive
      message.

---

## API Endpoint

### Generate Signature

- **Path**: `/v1/generate-signature` (when using API Gateway)
- **Method**: POST
- **Body**: Optional (current implementation generates timestamp and folder server-side; can be
  extended to accept custom parameters like `public_id` or `tags`).
- **Headers** (when using API Gateway):
    - `x-api-key`: API Gateway API key for authentication.
- **Success Response (200 OK)**:

```json
{
  "signature": "GENERATED_CLOUDINARY_SIGNATURE",
  "timestamp": 1678886400,
  "api_key": "YOUR_CLOUDINARY_API_KEY"
}
```

- **Error Responses**:
    - **405 Method Not Allowed**:
      ```json
      {
        "error": "Method not allowed. Please use POST."
      }
      ```
    - **500 Internal Server Error**:
      ```json
      {
        "error": "Server configuration error."
      }
      ```
      or
      ```json
      {
        "error": "Failed to generate signature: <specific error message>"
      }
      ```

---

## Environment Variables

The function requires the following environment variables, configured during deployment:

| Variable                | Description                               | Example Value     |
|-------------------------|-------------------------------------------|-------------------|
| `CLOUDINARY_CLOUD_NAME` | Your Cloudinary account’s cloud name.     | `your-cloud-name` |
| `CLOUDINARY_API_KEY`    | Your Cloudinary API key.                  | `your-api-key`    |
| `CLOUDINARY_API_SECRET` | Your Cloudinary API secret (keep secure). | `your-api-secret` |

> **Security Note**: Use Google Secret Manager for storing `CLOUDINARY_API_SECRET` in production to
> enhance security. Avoid hardcoding secrets in source code.

---

## IAM Permissions

The Google Cloud Function’s service account requires:

- **Default Permissions**: `roles/cloudfunctions.invoker` for HTTP invocation (if not
  `--allow-unauthenticated`).
- **Secret Manager (Optional)**: If using Google Secret Manager for Cloudinary secrets, grant
  `roles/secretmanager.secretAccessor` for the specific secrets.
- **Cloud Build**: Required during deployment (`roles/cloudbuild.builds.editor`).

No additional IAM permissions are needed for Cloudinary interactions, as credentials are provided
via environment variables.

---

## Prerequisites

Before deploying, ensure you have:

1. **Google Cloud Account**: With a project and billing enabled.
2. **Cloudinary Account**: Sign up at [cloudinary.com](https://cloudinary.com/) to obtain
   `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, and `CLOUDINARY_API_SECRET`.
3. **Google Cloud SDK**: Installed and configured (`gcloud` CLI).
4. **APIs Enabled**:
    - Cloud Functions API
    - Cloud Build API
    - API Gateway API (if using API Gateway)
    - Service Control API (if using API Gateway)
5. **Kotlin Development Environment**:
    - Gradle with ShadowJar plugin for building the JAR.
    - Cloudinary SDK for Kotlin/Java.
6. **Service Account**: For deployment and (optionally) API Gateway invocation.

---

## Deployment

Follow these steps to deploy the `CloudinarySignatureGenerator` function:

1. **Build the Function**:
    - The project uses the ShadowJar Gradle plugin to create a fat JAR with all dependencies.
    - Run the following command in the `cloudinarysignaturegenerator/` directory:
      ```bash
      ./gradlew :cloudinarysignaturegenerator:shadowjar
      ```
    - The output JAR will be in `build/libs/`.

2. **Deploy to Google Cloud Functions**:
    - Use the `gcloud` CLI to deploy:
      ```bash
      gcloud functions deploy cloudinary-signature-generator \
        --runtime java11 \
        --trigger-http \
        --entry-point com.github.adnanrangrej.cloudinarysignaturegenerator.CloudinarySignatureGenerator \
        --region YOUR_REGION \
        --source build/libs/CloudinarySignatureGenerator-all.jar \
        --set-env-vars CLOUDINARY_CLOUD_NAME=your_cloud_name,CLOUDINARY_API_KEY=your_api_key,CLOUDINARY_API_SECRET=your_api_secret \
        --allow-unauthenticated
      ```
    - Replace:
        - `YOUR_REGION`: e.g., `us-central1`.
        - `your_cloud_name`, `your_api_key`, `your_api_secret`: Your Cloudinary credentials.
    - **Note**: Remove `--allow-unauthenticated` if you require authentication, and configure a
      service account with `roles/cloudfunctions.invoker`.

3. **Verify Deployment**:
    - Note the function’s HTTP trigger URL (e.g.,
      `https://YOUR_REGION-YOUR_PROJECT_ID.cloudfunctions.net/cloudinary-signature-generator`).
    - Test with a `POST` request (see [Usage](#usage)).

> **Security Note**: For production, store `CLOUDINARY_API_SECRET` in Google Secret Manager and
> grant the function’s service account access. Update the code to fetch secrets at runtime.

---

## Exposing via API Gateway

For enhanced API management (e.g., API key authentication, rate limiting), deploy the function
behind Google Cloud API Gateway.

1. **Enable APIs**:
    - Enable `API Gateway API` and `Service Control API` in your Google Cloud project.

2. **Create an OpenAPI Specification**:
    - Save the following as `openapi-spec.yaml`:
      ```yaml
      swagger: '2.0'
      info:
        title: Cloudinary Signature API
        description: API to generate Cloudinary upload signatures
        version: '1.0.0'
      schemes:
        - https
      produces:
        - application/json
      securityDefinitions:
        api_key_header:
          type: apiKey
          name: x-api-key
          in: header
      paths:
        /v1/generate-signature:
          post:
            summary: Generates a Cloudinary signature
            operationId: generateCloudinarySignature
            security:
              - api_key_header: []
            x-google-backend:
              address: https://YOUR_REGION-YOUR_PROJECT_ID.cloudfunctions.net/cloudinary-signature-generator
            responses:
              '200':
                description: Signature successfully generated
                schema:
                  type: object
                  properties:
                    signature:
                      type: string
                    timestamp:
                      type: integer
                    api_key:
                      type: string
              '401':
                description: Unauthorized
              '405':
                description: Method Not Allowed
              '500':
                description: Internal Server Error
          options:
            summary: CORS support
            operationId: corsGenerateCloudinarySignature
            x-google-backend:
              address: https://YOUR_REGION-YOUR_PROJECT_ID.cloudfunctions.net/cloudinary-signature-generator
            responses:
              '204':
                description: No Content
                headers:
                  Access-Control-Allow-Origin:
                    type: string
                  Access-Control-Allow-Methods:
                    type: string
                  Access-Control-Allow-Headers:
                    type: string
                  Access-Control-Max-Age:
                    type: string
      ```
    - Replace
      `https://YOUR_REGION-YOUR_PROJECT_ID.cloudfunctions.net/cloudinary-signature-generator` with
      your function’s trigger URL.

3. **Create an API Config**:
    - Upload the OpenAPI spec:
      ```bash
      gcloud api-gateway api-configs create cloudinary-signature-config-v1 \
        --api=cloudinary-signature-api \
        --openapi-spec=openapi-spec.yaml \
        --project=YOUR_PROJECT_ID \
        --backend-auth-service-account=YOUR_GATEWAY_INVOKER_SERVICE_ACCOUNT_EMAIL
      ```
    - Replace `YOUR_PROJECT_ID` and `YOUR_GATEWAY_INVOKER_SERVICE_ACCOUNT_EMAIL` (if the function
      requires authentication).
    - If the API doesn’t exist, create it first:
      ```bash
      gcloud api-gateway apis create cloudinary-signature-api --project=YOUR_PROJECT_ID
      ```

4. **Create a Gateway**:
    - Deploy the API config:
      ```bash
      gcloud api-gateway gateways create cloudinary-signature-gateway \
        --api=cloudinary-signature-api \
        --api-config=cloudinary-signature-config-v1 \
        --location=YOUR_GATEWAY_REGION \
        --project=YOUR_PROJECT_ID
      ```
    - Replace `YOUR_GATEWAY_REGION` (e.g., `us-central1`).
    - Note the gateway URL (e.g., `https://cloudinary-signature-gateway-xxxx.gateway.dev`).

5. **Create and Restrict an API Key**:
    - In the Google Cloud Console, go to **APIs & Services** > **Credentials**.
    - Create an API key and restrict it to the `API Gateway API`.
    - Use this key in the `x-api-key` header for client requests.

6. **Update NatureGuardian App**:
    - Set `CLOUDINARY_BACKEND_URL` in `app/secrets.properties` to the gateway URL (e.g.,
      `https://cloudinary-signature-gateway-xxxx.gateway.dev/v1/generate-signature`).
    - Set `CLOUDINARY_BACKEND_URL_API_KEY` to the API Gateway API key.

---

## Usage

1. **Client-Side Integration**:
    - The **NatureGuardian** app sends a `POST` request to the API Gateway endpoint (
      `/v1/generate-signature`) with the `x-api-key` header.
    - Use the response (`signature`, `timestamp`, `api_key`) to construct a Cloudinary upload
      request.

2. **Testing with `curl`**:
    - **Direct Function Call** (if `--allow-unauthenticated`):
      ```bash
      curl -X POST https://YOUR_REGION-YOUR_PROJECT_ID.cloudfunctions.net/cloudinary-signature-generator
      ```
    - **Via API Gateway**:
      ```bash
      curl -X POST https://YOUR_GATEWAY_DEFAULT_HOSTNAME/v1/generate-signature -H "x-api-key: YOUR_API_GATEWAY_API_KEY"
      ```

3. **Cloudinary Upload**:
    - Use the response values in the client’s Cloudinary upload request:
      ```javascript
      const formData = new FormData();
      formData.append("file", file);
      formData.append("api_key", response.api_key);
      formData.append("timestamp", response.timestamp);
      formData.append("signature", response.signature);
      formData.append("folder", "profile_images");
      fetch("https://api.cloudinary.com/v1_1/YOUR_CLOUD_NAME/image/upload", {
        method: "POST",
        body: formData
      });
      ```

---

## Troubleshooting

- **405 Method Not Allowed**:
    - Ensure the request uses `POST` (not `GET` or other methods).
- **500 Internal Server Error**:
    - Verify `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, and `CLOUDINARY_API_SECRET` are set
      correctly.
    - Check Cloud Function logs in the Google Cloud Console.
- **401 Unauthorized (API Gateway)**:
    - Confirm the `x-api-key` header contains a valid API Gateway API key.
- **CORS Errors**:
    - Ensure the `OPTIONS` route is configured in API Gateway or the function handles CORS
      correctly.
- **Logs**:
    - View logs in the Google Cloud Console under **Cloud Functions** > **Logs**.

> **Cost Note**: Google Cloud Functions and API Gateway may incur charges based on invocation and
> compute time. Monitor usage in the Google Cloud Console.

---

## Contributing

Contributions to improve this function are welcome! To contribute:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m "Add YourFeature"`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

Please include:

- A clear description of changes.
- Updates to this README for new features or environment variables.
- Tests for new functionality (if applicable).

---

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE) file in the root
of the **NatureGuardian** repository for details.

MIT © 2025 Md Adnan