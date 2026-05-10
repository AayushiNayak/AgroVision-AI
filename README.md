# 🌱 AgroVision AI — Plant Disease Detection using Deep Learning

[![Python](https://img.shields.io/badge/Python-3.10-blue.svg)](https://www.python.org/)
[![TensorFlow](https://img.shields.io/badge/TensorFlow-2.15-orange.svg)](https://tensorflow.org/)
[![TensorFlow Lite](https://img.shields.io/badge/TFLite-Mobile%20Optimized-green.svg)](https://www.tensorflow.org/lite)
[![Accuracy](https://img.shields.io/badge/Accuracy-99.3%25-brightgreen.svg)](#)
[![Platform](https://img.shields.io/badge/Platform-Android-success.svg)](#)

An end-to-end AI-powered mobile solution for detecting **35 plant diseases** across **7 plant species** using Deep Learning and TensorFlow Lite.

This project combines:
- 🌿 Deep Learning Model Training
- 📱 Android Mobile Application
- ⚡ Offline Disease Detection
- 🤖 TensorFlow Lite Deployment

The system is designed to help farmers and agricultural workers identify plant diseases instantly using a smartphone camera — even without an internet connection.

---

# 🚀 Project Overview

Plant diseases cause major agricultural losses worldwide. Early diagnosis is critical, but many farmers lack access to fast and reliable disease detection systems.

**AgroVision AI** solves this problem by providing:
- Real-time plant disease prediction
- Offline mobile inference
- Lightweight optimized AI model
- High prediction accuracy
- Mobile-ready deployment using TensorFlow Lite

---

# ✨ Key Features

## 🌿 AI-Based Disease Detection
Detects 35 plant diseases using image classification.

## 📱 Android Application
Integrated TensorFlow Lite model for mobile deployment.

## ⚡ Offline Functionality
No internet connection required after installation.

## 🎯 High Accuracy
Achieved **99.3% validation accuracy** using transfer learning.

## 🚀 Fast Inference
Prediction time under 100ms on modern smartphones.

## 🧠 Transfer Learning
Built using EfficientNetV2-S pretrained architecture.

## 📷 Camera & Gallery Support
Users can:
- Capture leaf images using camera
- Upload images from gallery
- Receive instant predictions

---

# 🧠 Deep Learning Model Details

| Component | Details |
|---|---|
| Architecture | EfficientNetV2-S |
| Framework | TensorFlow 2.15 |
| Training Technique | Transfer Learning |
| Loss Function | Focal Loss |
| Deployment Format | TensorFlow Lite |
| Optimization | Float16 Quantization |
| Model Size | ~30 MB |
| Dataset | PlantVillage Dataset |

---

# 📊 Model Performance

| Metric | Score |
|---|---|
| Validation Accuracy | 99.3% |
| Mobile Inference Speed | <100ms |
| Disease Classes | 35 |
| Plant Species | 7 |

---

# 📸 Performance Showcase

## Real-World Predictions

![Success Gallery](Success_Gallery.png)

*Figure: Sample predictions showing extremely high confidence scores on unseen test images.*

---

# 📂 Repository Structure

```bash
AgroVision-AI/
│
├── Android_App/
│   ├── app/
│   ├── gradle/
│   ├── build.gradle
│   └── settings.gradle
│
├── Plant_Disease_Detection.ipynb
├── plant_care_offline.tflite
├── labels.txt
├── README.md
└── Success_Gallery.png
```

---

# 📱 Android Application

The Android app allows users to detect plant diseases directly from their smartphones.

## 🔥 Android App Features

- 📷 Real-time image capture
- 🖼️ Gallery image selection
- 🤖 TensorFlow Lite integration
- ⚡ Offline prediction
- 📊 Confidence score display
- 🌿 Disease label output
- 📱 Lightweight and fast UI

---

# 🛠️ Android Tech Stack

| Technology | Purpose |
|---|---|
| Java/Kotlin | Android Development |
| TensorFlow Lite | On-device AI inference |
| Android Studio | Development Environment |
| CameraX / Intent APIs | Image Capture |
| XML | UI Design |

---

# 📲 How to Run the Android App

## Step 1 — Clone the Repository

```bash
git clone https://github.com/your-username/AgroVision-AI.git
```

---

## Step 2 — Open in Android Studio

Open the `Android_App` folder using Android Studio.

---

## Step 3 — Add TensorFlow Lite Dependency

Inside `build.gradle`:

```gradle
implementation 'org.tensorflow:tensorflow-lite:2.14.0'
```

---

## Step 4 — Add Model Files

Place these files inside:

```bash
app/src/main/assets/
```

Files:
- `plant_care_offline.tflite`
- `labels.txt`

---

## Step 5 — Run the Application

Connect your Android device or emulator and click:

▶️ **Run App**

---

# 🧪 Model Training Pipeline

The notebook includes:
- Data preprocessing
- Data augmentation
- Transfer learning
- Model training
- Evaluation
- TensorFlow Lite conversion

Notebook:
```bash
Plant_Disease_Detection.ipynb
```

---

# 📚 Dataset

Dataset used:
## PlantVillage Dataset

Contains:
- Healthy plant leaves
- Diseased plant leaves
- Multiple crop categories

---

# 🔍 Supported Plant Categories

Examples include:
- Tomato
- Potato
- Pepper
- Corn
- Apple
- Grape
- Strawberry

---

# 🧠 Future Improvements

- 🌐 Cloud synchronization
- 🌡️ IoT sensor integration
- 🗣️ Voice assistance for farmers
- 🌍 Multilingual support
- 📍 GPS-based disease mapping
- ☁️ Firebase integration
- 📊 Disease history analytics

---

# 💼 Internship / Resume Highlights

This project demonstrates:
- Deep Learning
- Computer Vision
- Mobile AI Deployment
- TensorFlow Lite
- Android Development
- End-to-End AI Product Development

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a new branch
3. Commit changes
4. Submit a Pull Request

---

# 📜 License

This project is licensed under the MIT License.

---

# 👩‍💻 Author

### Aayushi Nayak
B.Tech CSE Student | AI & Android Enthusiast

- Deep Learning
- Mobile AI Applications
- TensorFlow Lite
- Android Development

---

# ⭐ If you found this project useful

Please consider:
- ⭐ Starring the repository
- 🍴 Forking the project
- 📢 Sharing with others

---
