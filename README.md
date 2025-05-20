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
> ```
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
* This APK is signed with a demo key and is intended for preview purposes only.
* It is pre-configured to connect to the project's demonstration backend services (where applicable and available). Functionality relying on these services may be rate-limited or subject to change.
* You may need to enable "Installation from Unknown Sources" on your Android device to install the APK.

---

## 🏗️ Project Structure

```bash
NatureGuardian/
├── app/                            # Main Android application (Jetpack Compose, Kotlin)
├── backend/                        # AWS Lambda functions: device registration & news notifier
├── apigateway/                     # AWS Lambda: API for fetching news from DynamoDB
├── cloudinarysignaturegenerator/   # Google Cloud Function: generates signatures for secure image uploads
├── naturebot/                      # Python Lambda: Google Gemini-powered AI species assistant
└── docs/                           # (Recommended) For screenshots, architecture diagrams, etc.
```

---

## ☁️ Lambdas & Cloud Functions

| Name                              | Stack   | Purpose                                            | Path                              |
|-----------------------------------|---------|----------------------------------------------------|-----------------------------------|
| **NewsNotifierLambda**            | Kotlin  | Fetch GNews & update DynamoDB every 15 min         | `/backend/NewsNotifierLambda/`    |
| **DeviceRegisterLambda**          | Kotlin  | Register devices to AWS SNS topic                  | `/backend/DeviceRegisterLambda/`  |
| **NewsApiLambda**                 | Kotlin  | API to fetch news from DynamoDB                    | `/apigateway/NewsApiLambda/`      |
| **CloudinarySignatureFunction**   | Kotlin  | Sign Cloudinary uploads via Google Cloud Run       | `/cloudinarysignaturegenerator/`  |
| **NatureGuardianBotLambda**       | Python  | Gemini API chatbot for species queries             | `/naturebot/`                     |

> Each folder contains its own `README.md` with setup & deployment steps.

---

## 🚀 Technologies Used

- **Android**: Jetpack Compose, Kotlin  
- **Local DB**: Room (CSV import)  
- **Maps**: Google Maps SDK + Marker Clustering  
- **AI**: Google Gemini API (Python Lambda)  
- **News**: GNews API, AWS Lambda, DynamoDB, EventBridge  
- **Notifications**: AWS SNS, Firebase Cloud Messaging  
- **Auth & Storage**: Firebase Auth, Firestore  
- **Media**: Cloudinary (signed uploads) via Google Cloud Run  

---

## 🛠️ Setup Instructions

> This demo requires you to configure **your own** cloud resources and API keys.

1. **Firebase**  
   - Place `google-services.json` in `app/`  
   - Enable Auth & Firestore

2. **Google Maps**  
   - Create `secrets.properties` in `app/`:  
     ```properties
     MAPS_API_KEY=YOUR_GOOGLE_MAPS_KEY
     ```

3. **AWS**  
   - Deploy Lambdas in `backend/` & `apigateway/`  
   - Set up:
     - DynamoDB table for news
     - SNS topic for notifications
     - API Gateway endpoints
     - EventBridge rule (15 min) for NewsNotifierLambda

4. **Google Cloud**  
   - Deploy Cloud Run service from `cloudinarysignaturegenerator/`  
   - Secure with Cloud IAM or API Gateway

5. **Python Lambda**  
   - Zip and deploy `/naturebot/` to AWS Lambda  
   - Set environment var `GEMINI_API_KEY` in its configuration

---

## 📂 Lambda & Function READMEs

For setup, deployment, and configuration of individual Lambdas:

- /backend/README.md
- /apigateway/README.md
- /cloudinarysignaturegenerator/README.md
- /naturebot/README.md

---

## 🙋 Limitations

- Not published to Play Store (no package signing)  
- Requires private keys & billing-enabled cloud accounts  
- Designed for **educational/demo** use only  

---

## 🤝 Contributing

1. Fork the repo  
2. Create a feature branch (`git checkout -b feature/XYZ`)  
3. Commit your changes (`git commit -m "Add XYZ"`)  
4. Push to the branch (`git push origin feature/XYZ`)  
5. Open a Pull Request

---

## 📄 License

MIT © 2025 Adnan Rangrej 
