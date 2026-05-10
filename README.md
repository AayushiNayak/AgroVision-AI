# # AgroVision AI 🌱

[![Python](https://img.shields.io/badge/Python-3.10-blue.svg)](https://www.python.org/)
[![TensorFlow](https://img.shields.io/badge/TensorFlow-2.15-orange.svg)](https://tensorflow.org/)
[![Accuracy](https://img.shields.io/badge/Accuracy-99.3%25-green.svg)](#)

An end-to-end Deep Learning solution to identify **35 different plant diseases** across 7 species. This project features a high-accuracy model optimized for **offline mobile deployment**.

## 🚀 The Solution
Agriculture faces massive losses due to undetected diseases. This project provides a **mobile-ready** model that allows farmers to diagnose plant health instantly, even without an internet connection.

### **Key Technical Stats:**
* **Architecture:** EfficientNetV2-S (Transfer Learning)
* **Optimization:** TFLite (Float16 Quantization)
* **Model Size:** ~30 MB (Optimized for Android/iOS)
* **Inference Speed:** <100ms on most modern smartphones

---

## 📊 Performance Showcase
Our model was trained on the PlantVillage dataset using **Focal Loss** to handle class imbalance, resulting in exceptional precision across all 35 categories.

### **Real-World Test Results**
![Success Gallery](Screenshot_2026-03-13_185227.jpg)
*Figure 1: Sample predictions from the test set showing 99.9% - 100% confidence scores.*

---

## 📂 Repository Structure
* `Plant_Disease_Detection.ipynb`: The complete training pipeline (Data Augmentation, Training, and Evaluation).
* `plant_care_offline.tflite`: The final, compressed model ready for Android Studio.
* `labels.txt`: Mapping file for the 38 disease classes.

---

## 🛠️ How to Deploy on Android
To use the included `.tflite` model in your app:

1.  **Add the model:** Place `plant_care_offline.tflite` and `labels.txt` in your Android Studio `assets` folder.
2.  **Add Dependency:** Add `implementation 'org.tensorflow:tensorflow-lite:2.14.0'` to your `build.gradle`.
3.  **Initialize:** Load the model using the TFLite Interpreter.
