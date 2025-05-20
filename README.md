# 🌿 NatureGuardian

<p align="center">
  <img src="https://raw.githubusercontent.com/adnanrangrej/nature-guardian/refs/heads/main/app/src/main/ic_launcher-playstore.png" height="200"/>
</p>

**NatureGuardian** is a feature-rich Android app that raises awareness about India's rare, endangered, and threatened (RET) species. It integrates AI chat assistance, species mapping, real-time news updates, and secure media uploads—using modern cloud services and serverless backends.

> ⚠️ **Educational Demo**  
> This project is **not deployed** to any app store. Clone & run only after setting up your own cloud credentials.

---

## 📱 Key Features

- 🔎 **Species Profiles**: 200+ species loaded from CSV into Room DB.
- 🤖 **AI-Powered NatureBot**: Ask questions about any species using a Gemini-powered chatbot.
- 🗺️ **Species Distribution Map**: Google Maps integration to visualize habitats.
- 📰 **Real-Time News**: Serverless AWS Lambda fetches latest conservation news every 15 minutes.
- 🔔 **Notifications**: Receive push updates using AWS SNS + Firebase Cloud Messaging.
- 🧾 **Firebase Auth + Cloudinary**: Signup/Login with profile support & image uploads.

---

## 📸 Screenshots / Demo APK

> Add your screenshots below — helps users see the UI at a glance!

<!--
<p align="center">
  <img src="docs/screenshot1.png" width="300"/>
  <img src="docs/screenshot2.png" width="300"/>
</p>
-->

---

## 📦 Demo APK

You can try out the app by downloading the demo APK here:

👉 [Download APK](https://github.com/adnanrangrej/nature-guardian/releases/download/v1.0-demo/app-release.apk)

⚠️ Make sure to allow installation from unknown sources on your device.

---


## 🏗️ Project Structure

```bash
NatureGuardian/
├── app/                            # Main Android app (Jetpack Compose)
├── backend/                        # AWS Lambdas: device register & news notifier
├── apigateway/                     # AWS Lambda: fetch news from DynamoDB
├── cloudinarysignaturegenerator/  # Google Cloud Function: image upload signature
├── naturebot/                      # Python Lambda: Gemini-powered species assistant
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
