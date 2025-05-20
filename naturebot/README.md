# NatureGuardianBot AWS Lambda Function

The `NatureGuardianBot` Lambda function powers the AI chatbot for the **NatureGuardian** app by
interfacing with the Google Gemini API. It processes user prompts, maintains conversation history,
and generates text-based responses, acting as "NatureBot," a conservation assistant focused on
biodiversity and wildlife, with an emphasis on Indian examples.

> ⚠️ **Educational Demo**  
> This function is part of the NatureGuardian project, intended for educational and demonstration
> purposes. Follow the setup instructions to configure your own AWS and Google Cloud resources.

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

The `NatureGuardianBot` Lambda function enables conversational AI capabilities in the *
*NatureGuardian** app. It queries the Google Gemini API to generate responses to user prompts about
biodiversity, species conservation, and related topics, maintaining context through conversation
history and customizable system instructions.

---

## Functionality

The function performs the following steps:

1. **Input Processing**:
    - Extracts `prompt` (required), `history` (optional), `systemInstruction` (optional), and
      `max_tokens` (optional) from the input JSON.
    - Validates inputs, applying defaults:
        - `systemInstruction`: Defaults to a predefined instruction for "NatureBot," focusing on
          conservation and Indian wildlife.
        - `max_tokens`: Defaults to 512, clamped between 10 and 2048.
        - `history`: Defaults to an empty list if invalid.

2. **History Conversion**:
    - Converts input `history` (list of `{role, content}` objects) to the `types.Content` format
      required by the Gemini API.
    - Skips invalid history entries and logs warnings.

3. **Gemini API Interaction**:
    - Initializes the `google.generativeai.Client` with credentials.
    - Constructs a payload combining `gemini_history` and the user `prompt`.
    - Calls `client.models.generate_content` with:
        - Model specified by `GEMINI_MODEL` (defaults to `gemini-2.0-flash`).
        - Configuration: `system_instruction`, `temperature=0.7`, `top_p=0.9`, `top_k=40`,
          `candidate_count=1`, `max_output_tokens`, `response_mime_type="text/plain"`,
          `presence_penalty=0.3`, `frequency_penalty=0.2`.

4. **Response Formatting**:
    - Extracts the generated text and role (`model`) from the Gemini response.
    - Returns a JSON response with `statusCode`, `body` (containing `response`, `is_error`, `error`,
      and `request_id`).

5. **Error Handling**:
    - Returns `400` for invalid inputs (e.g., missing `prompt`).
    - Returns `502` for Gemini API errors.
    - Returns `500` for other internal errors.
    - Uses `error_response` helper to standardize error outputs.

---

## Input and Output Formats

### Input Format

The function expects a JSON payload, typically via API Gateway:

```json
{
  "prompt": "Tell me about the conservation status of the Snow Leopard in India.",
  "history": [
    {
      "role": "user",
      "content": "What are some major biodiversity hotspots in India?"
    },
    {
      "role": "model",
      "content": "India has four major biodiversity hotspots: the Himalayas, the Western Ghats, the Indo-Burma region, and the Sundaland."
    }
  ],
  "systemInstruction": "You are NatureBot. Focus on Indian wildlife conservation.",
  "max_tokens": 1000
}
```

- **`prompt`** (String, Required): User’s current message or question.
- **`history`** (List of Objects, Optional): Past conversation messages, each with:
    - `role` (String): `"user"` or `"model"`.
    - `content` (String): Message text.
    - Defaults to `[]` if invalid.
- **`systemInstruction`** (String, Optional): Instructions for the Gemini model. Defaults to a
  conservation-focused instruction.
- **`max_tokens`** (Integer, Optional): Max tokens for the response (10–2048). Defaults to 512.

### Output Format

#### Success Response (HTTP 200)

```json
{
  "statusCode": 200,
  "body": {
    "response": {
      "text": "The Snow Leopard is listed as 'Vulnerable' on the IUCN Red List. In India, it's found in the Himalayan high altitudes...",
      "role": "model"
    },
    "is_error": false,
    "error": null
  },
  "request_id": "aws-request-id-example-12345"
}
```

#### Error Response (HTTP 400, 502, 500)

```json
{
  "statusCode": 400,
  "body": {
    "response": null,
    "is_error": true,
    "error": "Missing or invalid 'prompt'"
  },
  "request_id": "aws-request-id-example-67890"
}
```

---

## Environment Variables

The function requires the following environment variables, configured in the AWS Lambda console or
deployment:

| Variable         | Description                                          | Example Value           |
|------------------|------------------------------------------------------|-------------------------|
| `GEMINI_MODEL`   | Gemini model to use. Defaults to `gemini-2.0-flash`. | `gemini-1.5-pro-latest` |
| `GOOGLE_API_KEY` | Gemini api key to use.                               | `YOUR_GEMINI_API_KEY`   |

> **Security Note**: Store the service account key securely. Use AWS Secrets Manager for production
> to avoid including the key file in the deployment package.

---

## IAM Permissions

The Lambda function’s execution role requires the following permissions:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "arn:aws:logs:YOUR_REGION:YOUR_ACCOUNT_ID:log-group:/aws/lambda/NatureGuardianBot:*"
    },
    {
      "Effect": "Allow",
      "Action": "secretsmanager:GetSecretValue",
      "Resource": "arn:aws:secretsmanager:YOUR_REGION:YOUR_ACCOUNT_ID:secret:YOUR_SECRET_NAME"
    }
  ]
}
```

### Notes:

- Replace `YOUR_REGION`, `YOUR_ACCOUNT_ID`, and `YOUR_SECRET_NAME` with your actual values.
- The `secretsmanager:GetSecretValue` permission is only needed if using AWS Secrets Manager for
  credentials.
- If the Lambda runs in a VPC, configure outbound internet access (e.g., NAT Gateway or VPC
  Endpoint) to reach the Gemini API.

---

## Prerequisites

Before deploying, ensure you have:

1. **AWS Account**: Access to Lambda, API Gateway, IAM, CloudWatch, and Secrets Manager.
2. **Python Environment**:
    - Python 3.9–3.11 (matching the Lambda runtime).
    - `pip` for installing dependencies.
3. **AWS CLI**: Configured for your AWS account.
4. **Dependencies**:
    - `google-genai`, `google-api-core` Python package.
5. **API Gateway**: For exposing the Lambda as an HTTP endpoint.

---

## Deployment

Follow these steps to deploy the `NatureGuardianBot` Lambda function:

1. **Create the Deployment Package**:

    - Install dependencies into a `package` directory:
      ```bash
      pip install -r requirements.txt --target ./package
      ```
    - Copy the Lambda code (e.g., `lambda_function.py`) to the `package` directory:
      ```bash
      cp lambda_function.py package/
      ```
    - Create a ZIP file:
      ```bash
      cd package
      zip -r ../deployment_package.zip .
      cd ..
      ```

2. **Deploy to AWS Lambda**:
    - In the AWS Management Console, create a new Lambda function:
        - **Runtime**: Python 3.9, 3.10, or 3.11.
        - **Handler**: `lambda_function.lambda_handler`.
        - **Upload**: Upload `deployment_package.zip`.
    - Or use the AWS CLI:
      ```bash
      aws lambda create-function \
        --function-name NatureGuardianBot \
        --runtime python3.10 \
        --handler lambda_function.lambda_handler \
        --zip-file fileb://deployment_package.zip \
        --role arn:aws:iam::YOUR_ACCOUNT_ID:role/YOUR_LAMBDA_ROLE \
        --timeout 60 \
        --memory-size 512
      ```

3. **Set Environment Variables**:
    - In the Lambda configuration, add:
      ```plaintext
      GEMINI_MODEL=gemini-2.0-flash
      GOOGLE_API_KEY=YOU_GEMINI_API_KEY
      ```

4. **Assign IAM Role**:
    - Use a role with the permissions listed in [IAM Permissions](#iam-permissions).

5. **Configure API Gateway Trigger**:
    - In the AWS API Gateway console, create an HTTP API or REST API:
        - **Route**: `POST /naturebot`
        - **Integration**: Link to the `NatureGuardianBot` Lambda with proxy integration.
        - **CORS**: Enable CORS for client-side access.
        - **Mapping Template (REST API, optional)**:
          ```velocity
          $input.json('$')
          ```
        - Deploy to a stage (e.g., `prod`) to get the endpoint URL (e.g.,
          `https://your-api-id.execute-api.us-east-1.amazonaws.com/prod/naturebot`).
    - Update the NatureGuardian app’s configuration with this URL.

6. **Test the Function**:
    - Use the Lambda console’s test feature with a sample JSON payload (
      see [Input Format](#input-and-output-formats)).
    - Or test via API Gateway (see [Usage](#usage)).

> **Security Note**: For production, store the Google Cloud credentials in AWS Secrets Manager and
> update the code to fetch them at runtime. Avoid including `gcp_credentials.json` in the package.

---

## Usage

1. **Client-Side Integration**:
    - The **NatureGuardian** app sends `POST` requests to the API Gateway endpoint (`/naturebot`)
      with a JSON payload containing the user’s prompt and optional history.
    - Parse the response to display the AI’s text in the chatbot interface.

2. **Testing with `curl`**:
    - **Via API Gateway**:
      ```bash
      curl -X POST https://your-api-id.execute-api.us-east-1.amazonaws.com/prod/naturebot \
        -H "Content-Type: application/json" \
        -d '{
          "prompt": "Tell me about the conservation status of the Snow Leopard in India.",
          "history": [
            {"role": "user", "content": "What are biodiversity hotspots?"},
            {"role": "model", "content": "Biodiversity hotspots are regions with high species diversity under threat..."}
          ]
        }'
      ```

3. **Response Handling**:
    - Extract `body.response.text` for the chatbot display.
    - Handle errors (`body.is_error=true`) by showing the `body.error` message.

---

## Troubleshooting

- **400 Bad Request**:
    - Ensure the `prompt` is a non-empty string and `history` entries have valid `role` and
      `content`.
- **502 Gateway Error**:
    - Verify the `GOOGLE_APPLICATION_CREDENTIALS` file is included and accessible.
    - Check Gemini API quotas and service account permissions.
- **500 Internal Server Error**:
    - Review CloudWatch Logs (`/aws/lambda/NatureGuardianBot`) for details.
- **Network Errors (VPC)**:
    - Ensure the Lambda has outbound internet access to reach the Gemini API (e.g., via NAT
      Gateway).
- **CORS Errors**:
    - Confirm CORS is enabled in API Gateway for the `/naturebot` route.

> **Cost Note**: Gemini API calls, Lambda invocations, and API Gateway usage may incur charges.
> Monitor costs in AWS and Google Cloud Consoles.

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
- Updates to this README for new features or environment variables.
- Tests for new functionality (if applicable).

---

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE) file in the root
of the **NatureGuardian** repository for details.

MIT © 2025 Md Adnan