# NewsNotifier AWS Lambda Function

The `NewsNotifier` Lambda function is a critical component of the **NatureGuardian** backend,
designed to periodically fetch biodiversity and conservation news from the GNews API. It stores
articles in an AWS DynamoDB table and publishes notifications to an AWS SNS topic, enabling
real-time updates in the NatureGuardian app.

> ⚠️ **Educational Demo**  
> This Lambda function is part of the NatureGuardian project, intended for educational and
> demonstration purposes. Follow the setup instructions to configure your own AWS resources and GNews
> API key.

---

## Table of Contents

- [Purpose](#purpose)
- [Functionality](#functionality)
- [Input and Output Formats](#input-and-output-formats)
- [Environment Variables](#environment-variables)
- [IAM Permissions](#iam-permissions)
- [DynamoDB Table Structure](#dynamodb-table-structure)
- [External Dependencies](#external-dependencies)
- [Prerequisites](#prerequisites)
- [Deployment](#deployment)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Purpose

The `NewsNotifier` Lambda function fetches the latest news articles on biodiversity and conservation
from the GNews API, stores them in a DynamoDB table, and publishes notifications to an SNS topic for
downstream processing (e.g., push notifications to the NatureGuardian app). It runs on a schedule (
e.g., every 15 minutes) via Amazon EventBridge, ensuring users receive timely updates.

---

## Functionality

The Lambda function performs the following steps:

1. **Triggered Execution**: Invoked periodically by an Amazon EventBridge rule (e.g., every 15
   minutes).
2. **Fetch Last Processed Timestamp**: Retrieves the `lastPublishedAt` timestamp from a DynamoDB
   metadata table to track the most recent article processed. Defaults to 24 hours ago if no
   timestamp exists (e.g., first run).
3. **Fetch Latest News**:
    - Constructs a GNews API query for `"biodiversity OR conservation"`.
    - Uses Retrofit to fetch articles published after `lastPublishedAt` (plus one second to avoid
      overlap).
4. **Process Articles**:
    - If no new articles are found, logs the result and exits.
    - For each new article:
        - **Store Article**: Saves article details (title, description, content, URL, image, source)
          to a DynamoDB `news articles` table.
        - **Publish Notification**: Sends a JSON payload (containing title, description, image URL,
          and `publishedAt`) to an SNS topic, formatted for FCM compatibility.
5. **Update Timestamp**: Updates the `lastPublishedAt` timestamp in the DynamoDB metadata table with
   the latest article’s timestamp (if new articles were processed).
6. **Returns Response**: Outputs a string indicating the number of articles processed or a message
   if no new articles were found.

---

## Input and Output Formats

### Input Format

The function is triggered by Amazon EventBridge and does not expect a user-defined input payload.
The input is typically the EventBridge event object, which can be ignored:

```json
{
  "id": "event-id",
  "source": "aws.events",
  "time": "2025-05-21T00:00:00Z"
}
```

### Output Format

The function returns a simple string:

- **Success**: `"Processed X articles."` (where `X` is the number of new articles).
- **No Articles**: `"No new articles."`

---

## Environment Variables

The function requires the following environment variables, configured in the AWS Lambda console or
deployment configuration:

| Variable                   | Description                                                             | Example Value                                                    |
|----------------------------|-------------------------------------------------------------------------|------------------------------------------------------------------|
| `NEWS_ARTICLES_TABLE_NAME` | Name of the DynamoDB table for storing news articles.                   | `NatureGuardian-NewsArticles`                                    |
| `NEWS_METADATA_TABLE_NAME` | Name of the DynamoDB table for storing the `lastPublishedAt` timestamp. | `NatureGuardian-NewsMetadata`                                    |
| `GNEWS_API_KEY`            | API key for the GNews API (obtained from gnews.io).                     | `your-gnews-api-key`                                             |
| `SNS_TOPIC_ARN`            | ARN of the SNS topic for publishing notifications.                      | `arn:aws:sns:us-east-1:123456789012:NatureGuardianNotifications` |
| `AWS_REGION`               | AWS region for DynamoDB and SNS resources.                              | `us-east-1`                                                      |

These are accessed in the Kotlin code using utility functions like `getNewsArticlesTableName()`,
`getNewsMetaDataTableName()`, `getApiKey()`, `getSnsTopicArn()`, and `getRegion()`.

> **Security Note**: Store the `GNEWS_API_KEY` securely in environment variables, not in source
> code. Use AWS Secrets Manager for enhanced security if needed.

---

## IAM Permissions

The Lambda function’s execution role requires the following permissions to interact with DynamoDB,
SNS, and CloudWatch Logs:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "dynamodb:GetItem",
        "dynamodb:PutItem"
      ],
      "Resource": [
        "arn:aws:dynamodb:YOUR_REGION:YOUR_ACCOUNT_ID:table/NatureGuardian-NewsArticles",
        "arn:aws:dynamodb:YOUR_REGION:YOUR_ACCOUNT_ID:table/NatureGuardian-NewsMetadata"
      ]
    },
    {
      "Effect": "Allow",
      "Action": "sns:Publish",
      "Resource": "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:NatureGuardianNotifications"
    },
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "arn:aws:logs:YOUR_REGION:YOUR_ACCOUNT_ID:log-group:/aws/lambda/NewsNotifier:*"
    }
  ]
}
```

### Notes:

- Replace `YOUR_REGION`, `YOUR_ACCOUNT_ID`, `NatureGuardian-NewsArticles`,
  `NatureGuardian-NewsMetadata`, and `NatureGuardianNotifications` with your actual values.
- Ensure DynamoDB table names match the environment variables.
- Scope `Resource` ARNs to specific tables and topics for better security.

---

## DynamoDB Table Structure

### 1. News Articles Table (`NEWS_ARTICLES_TABLE_NAME`)

- **Primary Key (Composite)**:
    - **Partition Key**: `id` (String) - Static value (e.g., `"News"`) or a category.
    - **Sort Key**: `publishedAt` (String) - ISO 8601 timestamp (e.g., `"2025-05-21T12:00:00Z"`).
- **Attributes**:
    - `title` (String): Article title.
    - `description` (String): Article summary.
    - `content` (String): Article content.
    - `url` (String): URL to the original article.
    - `image` (String): URL to the article’s image.
    - `sourceName` (String): Name of the news source.
    - `sourceUrl` (String): URL of the news source.

### 2. News Metadata Table (`NEWS_METADATA_TABLE_NAME`)

- **Primary Key**:
    - **Partition Key**: `id` (String) - Static value (e.g., `"lastPublishedAt"`).
- **Attributes**:
    - `timestamp` (String): ISO 8601 timestamp of the last processed article.

### Setup:

1. In the AWS DynamoDB console, create two tables with the above structures.
2. Set appropriate read/write capacity modes (e.g., On-Demand for simplicity or Provisioned for cost
   optimization).
3. Update the environment variables (`NEWS_ARTICLES_TABLE_NAME`, `NEWS_METADATA_TABLE_NAME`) with
   the table names.

---

## External Dependencies

- **GNews API**: Requires a valid API key from [gnews.io](https://gnews.io/). Sign up and obtain the
  key, then set it as the `GNEWS_API_KEY` environment variable.
- **AWS SDK for Kotlin**: Used for DynamoDB and SNS interactions.
- **Retrofit**: Handles HTTP requests to the GNews API.

---

## Prerequisites

Before deploying, ensure you have:

1. **AWS Account**: With access to Lambda, DynamoDB, SNS, EventBridge, IAM, and CloudWatch.
2. **GNews API Key**: Obtained from [gnews.io](https://gnews.io/).
3. **DynamoDB Tables**: Created with the structure described
   in [DynamoDB Table Structure](#dynamodb-table-structure).
4. **SNS Topic**: Created in AWS SNS (e.g., `NatureGuardianNotifications`).
5. **Kotlin Development Environment**:
    - Gradle with ShadowJar plugin for building the JAR.
    - Dependencies: AWS SDK for Kotlin, Retrofit, OkHttp.
6. **AWS CLI or SDK**: Optional, for automated deployment.

---

## Deployment

Follow these steps to deploy the `NewsNotifier` Lambda function:

1. **Build the Lambda**:
    - This project uses the ShadowJar Gradle plugin to create a fat JAR with all dependencies.
    - Run the following command in the `backend/NewsNotifierLambda/` directory:
      ```bash
      ./gradlew :backend:shadowJar
      ```
    - The output JAR will be in `build/libs/` (e.g., `NewsNotifier-all.jar`).

2. **Create the Lambda Function**:
    - In the AWS Management Console, navigate to Lambda and create a new function.
    - Choose **Java 11 (Corretto)** as the runtime.
    - Upload the generated JAR from `build/libs/`.

3. **Configure the Handler**:
    - Set the handler to: `com.github.adnanrangrej.backend.newsnotifier.NewsNotifier::handleRequest`

4. **Set Environment Variables**:
    - In the Lambda configuration, add:
      ```plaintext
      NEWS_ARTICLES_TABLE_NAME=NatureGuardian-NewsArticles
      NEWS_METADATA_TABLE_NAME=NatureGuardian-NewsMetadata
      GNEWS_API_KEY=your-gnews-api-key
      SNS_TOPIC_ARN=arn:aws:sns:us-east-1:123456789012:NatureGuardianNotifications
      AWS_REGION=us-east-1
      ```

5. **Assign IAM Role**:
    - Create or use an existing IAM role with the permissions listed
      in [IAM Permissions](#iam-permissions).
    - Attach the role to the Lambda function.

6. **Configure EventBridge Trigger**:
    - In the AWS EventBridge console, create a new rule:
        - **Name**: e.g., `NewsNotifierSchedule`
        - **Schedule Expression**: `cron(0/15 * * * ? *)` (every 15 minutes).
        - **Target**: Select the `NewsNotifier` Lambda function.
    - Enable the rule to start triggering the Lambda.

7. **Test the Function**:
    - In the Lambda console, use the test feature with an empty JSON payload (`{}`) to simulate an
      EventBridge trigger.
    - Check CloudWatch Logs for output (e.g., `"Processed X articles."`).

---

## Usage

1. **Automatic Execution**:
    - Once the EventBridge rule is active, the Lambda runs every 15 minutes, fetching new articles,
      storing them in DynamoDB, and publishing notifications to the SNS topic.

2. **Client-Side Integration**:
    - The **NatureGuardian** app retrieves articles via the `NewsApiLambda` (not this function).
    - Devices subscribed to the SNS topic (via `DeviceRegistration` Lambda) receive push
      notifications for new articles.

3. **Manual Testing**:
    - Trigger the Lambda manually via the AWS console or CLI:
      ```bash
      aws lambda invoke --function-name NewsNotifier --payload '{}' output.json
      ```
    - Check `output.json` for the response and CloudWatch Logs for details.

---

## Troubleshooting

- **Error: "No new articles"**:
    - Verify the GNews API key is valid and has not exceeded its quota.
    - Ensure the `lastPublishedAt` timestamp in the metadata table is not too recent, preventing new
      articles from being fetched.
- **DynamoDB Errors**:
    - Confirm table names match the environment variables.
    - Check IAM permissions for `dynamodb:GetItem` and `dynamodb:PutItem`.
- **SNS Errors**:
    - Ensure the `SNS_TOPIC_ARN` is correct and the IAM role has `sns:Publish` permissions.
- **GNews API Errors**:
    - Check network connectivity and the GNews API status.
    - Validate the query (`"biodiversity OR conservation"`) and API key.
- **Logs**:
    - Review CloudWatch Logs (`/aws/lambda/NewsNotifier`) for detailed error messages.

> **Cost Note**: Running this Lambda every 15 minutes and using DynamoDB/SNS may incur AWS charges.
> Use On-Demand pricing for simplicity or Provisioned capacity for cost optimization.

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