# 🌿 NatureGuardian

<p align="center">
  <img src="https://raw.githubusercontent.com/adnanrangrej/nature-guardian/refs/heads/main/app/src/main/ic_launcher-playstore.png" height="200" alt="NatureGuardian App Icon"/>
</p>

**NatureGuardian** is a feature-rich Android app designed to raise awareness about India's rare, endangered, and threatened (RET) species. It integrates AI chat assistance, species mapping, real-time news updates, and secure media uploads—all powered by modern cloud services and serverless backends.

> ⚠️ **Educational Demo & Development Project**  
> This project is provided as an educational demo. The downloadable APK is for preview purposes. To build and run the project from source, or to deploy the backend services, you will need to set up and configure your own cloud credentials and API keys as outlined below.

---

## 📱 Key Features

- 🔎 **Species Profiles**: Detailed information on 200+ species, loaded from a local CSV into a Room database.
- 🤖 **AI-Powered NatureBot**: An interactive chatbot (powered by Google Gemini) to answer questions about any species.
- 🗺️ **Species Distribution Map**: Visualize species habitats using Google Maps SDK with marker clustering.
- 📰 **Real-Time News**: A serverless AWS Lambda function fetches the latest conservation news every 15 minutes.
- 🔔 **Push Notifications**: Receive updates using AWS SNS integrated with Firebase Cloud Messaging.
- 🧾 **User Authentication & Media**: Secure user signup/login via Firebase Authentication, with profile support and image uploads to Cloudinary.

---

## 📸 Screenshots / Demo Video

> **Note to Contributors:** Please add screenshots or a short demo video here to showcase the app's user interface and key functionalities!  
>
> Example:  
> ```html
> <p align="center">
>   <img src="docs/screenshot_species_list.png" width="250" alt="Screenshot of Species List"/>
>   <img src="docs/screenshot_map_view.png" width="250" alt="Screenshot of Map View"/>
>   <img src="docs/screenshot_chatbot.png" width="250" alt="Screenshot of Chatbot"/>
> </p>
> ```

---

## 📦 Demo APK for Preview

You can try out a pre-built version of the app by downloading the demo APK from our GitHub Releases:

👉 **[Download Demo APK (v1.0-demo)](https://github.com/adnanrangrej/nature-guardian/releases/download/v1.0-demo/app-release.apk)**

**Notes for Demo APK:**
- This APK is signed with a demo key and is intended for preview purposes only.
- It is pre-configured to connect to the project's demonstration backend services (where applicable and available). Functionality relying on these services may be rate-limited or subject to change.
- You may need to enable "Installation from Unknown Sources" on your Android device to install the APK.

---

## 🏗️ Project Structure

```bash
NatureGuardian/
├── app/                            # Main Android application (Jetpack Compose, Kotlin)
├── backend/                        # AWS Lambda functions: device registration & news notifier
├── apigateway/                     # AWS Lambda: API for fetching news from DynamoDB
├── cloudinarysignaturegenerator/   # Google Cloud Function: generates signatures for secure image uploads
├── naturebot/                      # Python Lambda: Google Gemini-powered AI species assistant
└── docs/                           # For screenshots, architecture diagrams, etc.
```

### ☁️ Backend Services: Lambdas & Cloud Functions
The backend for NatureGuardian is composed of several serverless functions:

| Name                        | Technology        | Purpose                                                                 | Path in Repository                     |
|-----------------------------|-------------------|-------------------------------------------------------------------------|---------------------------------------|
| NewsNotifierLambda          | Kotlin (AWS)      | Fetches GNews articles & updates DynamoDB (scheduled via EventBridge)   | `/backend/NewsNotifierLambda/`        |
| DeviceRegisterLambda        | Kotlin (AWS)      | Registers devices to an AWS SNS topic for push notifications            | `/backend/DeviceRegisterLambda/`      |
| NewsApiLambda               | Kotlin (AWS)      | Provides an API Gateway endpoint to fetch news from DynamoDB            | `/apigateway/NewsApiLambda/`          |
| CloudinarySignatureFunction | Kotlin (GCP)      | Generates secure signatures for Cloudinary uploads (via Google Cloud Run) | `/cloudinarysignaturegenerator/`      |
| NatureGuardianBotLambda     | Python (AWS)      | Powers the AI chatbot using the Google Gemini API                       | `/naturebot/`                         |

> Each backend service folder contains its own `README.md` with detailed setup, deployment, and configuration instructions.

---

## 🚀 Technologies & Libraries Used

- **Android UI**: Jetpack Compose
- **Language**: Kotlin
- **Local Database**: Android Room (with CSV data import)
- **Mapping**: Google Maps SDK for Android, Jetpack Maps Compose, Marker Clustering
- **AI Chatbot**: Google Gemini API (via Python Lambda)
- **News Aggregation**: GNews API, AWS Lambda, Amazon DynamoDB, Amazon EventBridge (for scheduling)
- **Push Notifications**: AWS SNS, Firebase Cloud Messaging (FCM)
- **Authentication**: Firebase Authentication
- **User Data Storage**: Firebase Firestore (for user profiles, etc.)
- **Media Uploads**: Cloudinary (for image storage, with signed uploads via Google Cloud Run)
- **Networking**: Retrofit, OkHttp
- **Dependency Injection**: Hilt (Dagger)
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose
- **Secrets Management**: Secrets Gradle Plugin for Android

---

## 🛠️ Setup Instructions (For Building from Source)

To build and run the NatureGuardian Android app from source, or to deploy your own backend, you'll need to configure your own cloud resources and API keys.

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/adnanrangrej/nature-guardian.git
   cd nature-guardian
   ```

2. **Android App Setup (`app/` directory)**:

   a. **Firebase**:
   - Go to the [Firebase Console](https://console.firebase.google.com/) and create a new Android project.
   - Register your app with the package name `com.github.adnanrangrej.natureguardian` (or your own if you change it).
   - Download the `google-services.json` file and place it in the `app/` directory of the cloned project.
   - Enable Firebase Authentication (e.g., Email/Password sign-in).
   - Enable Cloud Firestore and set up basic security rules.

   b. **Secrets Configuration (`app/secrets.properties`)**:
   The Android app uses the Secrets Gradle Plugin to manage API keys. Create a file named `secrets.properties` in the `app/` directory with the following keys (replace `YOUR_..._KEY` with your actual values):

   ```properties
   MAPS_API_KEY=YOUR_Maps_API_KEY
   BACKEND_BASE_URL=YOUR_DEPLOYED_NEWS_API_GATEWAY_ENDPOINT_URL
   CLOUDINARY_CLOUD_NAME=YOUR_CLOUDINARY_CLOUD_NAME
   CLOUDINARY_BACKEND_URL=YOUR_DEPLOYED_CLOUDINARY_SIGNATURE_FUNCTION_URL
   CLOUDINARY_BACKEND_URL_API_KEY=YOUR_CLOUDINARY_SIGNATURE_FUNCTION_API_KEY_IF_ANY
   # Note: The GEMINI_API_KEY for the NatureBot is configured in its Python Lambda environment, not here.
   ```

   - `MAPS_API_KEY`: Your API key for the Google Maps SDK for Android.
   - `BACKEND_BASE_URL`: The base URL of your deployed News API Lambda (via API Gateway).
   - `CLOUDINARY_CLOUD_NAME`: Your Cloudinary account's cloud name.
   - `CLOUDINARY_BACKEND_URL`: The URL of your deployed Cloudinary Signature Generator function (e.g., on Google Cloud Run).
   - `CLOUDINARY_BACKEND_URL_API_KEY`: An API key if you've secured your signature generation endpoint.

   Ensure this `secrets.properties` file is **not** committed to your version control (it should be in `.gitignore`).

   c. **Build the App**:
   Open the project in Android Studio, let Gradle sync, and then build and run on an emulator or device.

3. **Backend Services Setup**:

   For each service in the `backend/`, `apigateway/`, `cloudinarysignaturegenerator/`, and `naturebot/` directories:
   - Navigate into the respective directory.
   - Follow the instructions in its `README.md` file for setup, configuration (including API keys like `GEMINI_API_KEY` for `naturebot`), and deployment to AWS or Google Cloud.
   - You will need to set up:
     - **AWS**: DynamoDB table (for news), SNS topic (for notifications), API Gateway endpoints, IAM roles, and an EventBridge rule (e.g., 15-minute schedule for `NewsNotifierLambda`).
     - **Google Cloud**: A Cloud Run service for the Cloudinary signature generator (secure it appropriately).
     - **Cloudinary**: A Cloudinary account.
     - **Google Gemini**: An API key for the NatureBot.

---

## 📂 Detailed Backend READMEs

For specific setup, deployment, and configuration of individual backend services, please refer to their dedicated README files:

- `backend/README.md`
- `apigateway/README.md`
- `cloudinarysignaturegenerator/README.md`
- `naturebot/README.md`

---

## 🙋 Known Limitations & Considerations

- **Demo APK Signing**: The provided demo APK is signed with a temporary demonstration key, not a production Play Store key.
- **Backend Costs**: Deploying the backend services will require accounts with AWS and Google Cloud, which may incur costs based on usage.
- **Educational Focus**: This project is primarily for educational and demonstration purposes. While comprehensive, it may not cover all production-level hardening or optimization aspects.
- **API Key Security**: Always handle your API keys and cloud credentials securely. Do not commit them to your repository.

---

## 🤝 Contributing

Contributions are welcome! If you'd like to improve NatureGuardian:

1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature/YourAmazingFeature`).
3. Commit your changes (`git commit -m "Add YourAmazingFeature"`).
4. Push to the branch (`git push origin feature/YourAmazingFeature`).
5. Open a Pull Socialism6. Please ensure your code adheres to the existing style and that any new backend components also include a `README.md` for setup.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

MIT © 2025 Adnan Rangrej
