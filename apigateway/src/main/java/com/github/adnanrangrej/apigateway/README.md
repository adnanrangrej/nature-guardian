# NewsApiLambda AWS Lambda Function

The `NewsApiLambda` function serves as a backend API endpoint for the **NatureGuardian** app,
retrieving news articles stored in an AWS DynamoDB table (populated by the `NewsNotifier` Lambda).
It supports two endpoints: one to fetch all news articles with pagination and another to retrieve a
specific article by its publication timestamp.

> ⚠️ **Educational Demo**  
> This Lambda function is part of the NatureGuardian project, intended for educational and
> demonstration purposes. Follow the setup instructions to configure your own AWS resources.

---

## Table of Contents

- [Purpose](#purpose)
- [Functionality](#functionality)
- [API Endpoints](#api-endpoints)
- [Environment Variables](#environment-variables)
- [IAM Permissions](#iam-permissions)
- [DynamoDB Table Structure](#dynamodb-table-structure)
- [Prerequisites](#prerequisites)
- [Deployment](#deployment)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Purpose

The `NewsApiLambda` function provides HTTP endpoints via Amazon API Gateway to allow the *
*NatureGuardian** app to fetch biodiversity and conservation news articles stored in a DynamoDB
table. It supports paginated retrieval of all articles and fetching a specific article by its
`publishedAt` timestamp, enabling efficient and flexible access to news data.

---

## Functionality

The Lambda function is triggered by API Gateway and routes requests based on the HTTP path:

1. **GET `/news`**:
    - Retrieves a paginated list of news articles from the DynamoDB table.
    - Supports pagination via a `nextToken` query parameter (Base64-encoded `LastEvaluatedKey`).
    - Returns articles in reverse chronological order (newest first) with a default limit of 20
      items per page.

2. **GET `/news/{publishedAt}`**:
    - Retrieves a single news article by its `publishedAt` timestamp (the sort key in DynamoDB).
    - Returns the article’s details or a 404 error if no matching article is found.

---

## API Endpoints

### 1. Get All News Articles

- **Path**: `/news`
- **Method**: GET
- **Query Parameters**:
    - `nextToken` (optional, String): Base64-encoded `LastEvaluatedKey` for pagination.
- **Success Response (200 OK)**:

```json
{
  "items": [
    {
      "id": {
        "S": "News"
      },
      "publishedAt": {
        "S": "2025-05-21T10:00:00Z"
      },
      "title": {
        "S": "Article Title 1"
      },
      "description": {
        "S": "Description of article 1..."
      },
      "content": {
        "S": "Full content of article 1..."
      },
      "url": {
        "S": "https://example.com/article1"
      },
      "image": {
        "S": "https://example.com/image1.jpg"
      },
      "sourceName": {
        "S": "Source Name 1"
      },
      "sourceUrl": {
        "S": "https://source1.example.com"
      }
    }
  ],
  "nextToken": "BASE64_ENCODED_LAST_EVALUATED_KEY_STRING"
}
```

- **Error Responses**:
    - **500 Internal Server Error**: Unexpected error during DynamoDB query.

### 2. Get Specific News Article

- **Path**: `/news/{publishedAt}`
- **Method**: GET
- **Path Parameters**:
    - `publishedAt` (required, String): ISO 8601 timestamp (e.g., `"2025-05-21T10:00:00Z"`) matching
      the article’s sort key.
- **Success Response (200 OK)**:

```json
{
  "id": {
    "S": "News"
  },
  "publishedAt": {
    "S": "2025-05-21T10:00:00Z"
  },
  "title": {
    "S": "Article Title 1"
  },
  "description": {
    "S": "Description of article 1..."
  },
  "content": {
    "S": "Full content of article 1..."
  },
  "url": {
    "S": "https://example.com/article1"
  },
  "image": {
    "S": "https://example.com/image1.jpg"
  },
  "sourceName": {
    "S": "Source Name 1"
  },
  "sourceUrl": {
    "S": "https://source1.example.com"
  }
}
```

- **Error Responses**:
    - **404 Not Found**: No article matches the `publishedAt` timestamp or invalid path.
    - **500 Internal Server Error**: Unexpected error during DynamoDB retrieval.

### Pagination (`nextToken`)

- The `/news` endpoint uses a `nextToken` for pagination, which is a Base64-encoded JSON string of
  DynamoDB’s `LastEvaluatedKey`.
- Clients pass the `nextToken` from a previous response in the `nextToken` query parameter to fetch
  the next page.
- If `nextToken` is `null` or absent in the response, no more items are available.

---

## Environment Variables

The function requires the following environment variables, configured in the AWS Lambda console or
deployment configuration:

| Variable                   | Description                                       | Example Value                 |
|----------------------------|---------------------------------------------------|-------------------------------|
| `NEWS_ARTICLES_TABLE_NAME` | Name of the DynamoDB table storing news articles. | `NatureGuardian-NewsArticles` |
| `AWS_REGION`               | AWS region for the DynamoDB table.                | `us-east-1`                   |

These are accessed in the Kotlin code using utility functions like `getNewsArticlesTableName()` and
`getRegion()`.

> **Security Note**: Ensure environment variables are securely configured and not hardcoded in the
> source code.

---

## IAM Permissions

The Lambda function’s execution role requires the following permissions for DynamoDB and CloudWatch
Logs:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "dynamodb:GetItem",
        "dynamodb:Query"
      ],
      "Resource": "arn:aws:dynamodb:YOUR_REGION:YOUR_ACCOUNT_ID:table/NatureGuardian-NewsArticles"
    },
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "arn:aws:logs:YOUR_REGION:YOUR_ACCOUNT_ID:log-group:/aws/lambda/NewsApiLambda:*"
    }
  ]
}
```

### Notes:

- Replace `YOUR_REGION`, `YOUR_ACCOUNT_ID`, and `NatureGuardian-NewsArticles` with your actual
  values.
- Scope the `Resource` ARN to the specific DynamoDB table for security.
- CloudWatch Logs permissions enable logging for debugging.

---

## DynamoDB Table Structure

### News Articles Table (`NEWS_ARTICLES_TABLE_NAME`)

- **Primary Key (Composite)**:
    - **Partition Key**: `id` (String) - Static value (e.g., `"News"`).
    - **Sort Key**: `publishedAt` (String) - ISO 8601 timestamp (e.g., `"2025-05-21T10:00:00Z"`).
- **Attributes**:
    - `title` (String): Article title.
    - `description` (String): Article summary.
    - `content` (String): Article content.
    - `url` (String): URL to the original article.
    - `image` (String): URL to the article’s image.
    - `sourceName` (String): Name of the news source.
    - `sourceUrl` (String): URL of the news source.

### Setup:

1. In the AWS DynamoDB console, create a table named `NatureGuardian-NewsArticles` (or as specified
   in `NEWS_ARTICLES_TABLE_NAME`).
2. Set the partition key as `id` (String) and sort key as `publishedAt` (String).
3. Use On-Demand capacity for simplicity or Provisioned capacity for cost optimization.

---

## Prerequisites

Before deploying, ensure you have:

1. **AWS Account**: Access to Lambda, DynamoDB, API Gateway, IAM, and CloudWatch.
2. **DynamoDB Table**: Created with the structure described
   in [DynamoDB Table Structure](#dynamodb-table-structure).
3. **Kotlin Development Environment**:
    - Gradle with ShadowJar plugin for building the JAR.
    - AWS SDK for Kotlin (`aws-sdk-kotlin`).
4. **API Gateway**: Configured to trigger the Lambda with the specified routes.
5. **AWS CLI or SDK**: Optional, for automated deployment.

---

## Deployment

Follow these steps to deploy the `NewsApiLambda` function:

1. **Build the Lambda**:
    - This project uses the ShadowJar Gradle plugin to create a fat JAR with all dependencies.
    - Run the following command in the `apigateway/NewsApiLambda/` directory:
      ```bash
      ./gradlew :apigateway:shadowJar
      ```
    - The output JAR will be in `build/libs/`.

2. **Create the Lambda Function**:
    - In the AWS Management Console, navigate to Lambda and create a new function.
    - Choose **Java 11 (Corretto)** as the runtime.
    - Upload the generated JAR from `build/libs/`.

3. **Configure the Handler**:
    - Set the handler to: `com.github.adnanrangrej.apigateway.news.NewsApiLambda::handleRequest`

4. **Set Environment Variables**:
    - In the Lambda configuration, add:
      ```plaintext
      NEWS_ARTICLES_TABLE_NAME=NatureGuardian-NewsArticles
      AWS_REGION=us-east-1
      ```

5. **Assign IAM Role**:
    - Create or use an existing IAM role with the permissions listed
      in [IAM Permissions](#iam-permissions).
    - Attach the role to the Lambda function.

6. **Configure API Gateway Trigger**:
    - In the AWS API Gateway console, create an HTTP API or REST API:
        - **Routes**:
            - `GET /news`
            - `GET /news/{publishedAt}`
        - **Integration**: Link each route to the `NewsApiLambda` function using Lambda proxy
          integration.
        - **CORS**: Enable CORS for client-side access (e.g., from the NatureGuardian app).
        - **Mapping Template (REST API)**: If using REST API, add a mapping template to handle
          query/path parameters:
          ```velocity
          {
            "path": "$context.resourcePath",
            "queryStringParameters": $input.params().querystring,
            "pathParameters": $input.params().path
          }
          ```
        - Deploy the API to a stage (e.g., `prod`) to get the endpoint URL (e.g.,
          `https://your-api-id.execute-api.us-east-1.amazonaws.com/prod`).
    - Update the `BACKEND_BASE_URL` in the NatureGuardian app’s `secrets.properties` with this URL.

7. **Test the Function**:
    - Use the Lambda console’s test feature or send HTTP requests to the API Gateway endpoints (
      see [Usage](#usage)).

---

## Usage

1. **Client-Side Integration**:
    - The **NatureGuardian** app sends GET requests to the API Gateway endpoints to fetch news
      articles.
    - Use the endpoint URL (e.g.,
      `https://your-api-id.execute-api.us-east-1.amazonaws.com/prod/news`).

2. **Testing with `curl`**:
    - **Get All Articles**:
      ```bash
      curl -X GET "https://your-api-id.execute-api.us-east-1.amazonaws.com/prod/news"
      ```
        - With `nextToken`:
          ```bash
          curl -X GET "https://your-api-id.execute-api.us-east-1.amazonaws.com/prod/news?nextToken=BASE64_ENCODED_TOKEN"
          ```
    - **Get Specific Article**:
      ```bash
      curl -X GET "https://your-api-id.execute-api.us-east-1.amazonaws.com/prod/news/2025-05-21T10:00:00Z"
      ```

3. **Response Handling**:
    - Parse the JSON response to display articles in the app.
    - For paginated results, store the `nextToken` and include it in subsequent requests to fetch
      more articles.

---

## Troubleshooting

- **404 Not Found**:
    - Ensure the `publishedAt` timestamp matches the sort key in DynamoDB exactly (e.g.,
      `"2025-05-21T10:00:00Z"`).
    - Verify the API Gateway route is correctly configured (`/news/{publishedAt}`).
- **500 Internal Server Error**:
    - Check the DynamoDB table name and IAM permissions.
    - Review CloudWatch Logs (`/aws/lambda/NewsApiLambda`) for detailed errors.
- **CORS Errors**:
    - Ensure CORS is enabled in API Gateway for the `/news` and `/news/{publishedAt}` routes.
- **Pagination Issues**:
    - Verify the `nextToken` is correctly Base64-encoded and matches the `LastEvaluatedKey` format.
- **Logs**:
    - Check CloudWatch Logs for debugging information.

> **Cost Note**: API Gateway and DynamoDB usage may incur AWS charges. Use On-Demand pricing for
> simplicity or Provisioned capacity for cost optimization.

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