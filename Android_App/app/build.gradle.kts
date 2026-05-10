plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.plantdiseaseapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.plantdiseaseapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = false
    }

    androidResources {
        noCompress += "tflite"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Forced upgrade to 2.17.0 to support the new FULLY_CONNECTED v12 opcode
    val tfliteVersion = "2.17.0"
    implementation("org.tensorflow:tensorflow-lite:$tfliteVersion")
    implementation("org.tensorflow:tensorflow-lite-gpu:$tfliteVersion")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4") {
        // This prevents the duplicate class error by excluding the old API
        exclude(group = "org.tensorflow", module = "tensorflow-lite-api")
    }
}

// Global fix for duplicate class errors between LiteRT and TFLite
configurations.all {
    exclude(group = "org.tensorflow", module = "tensorflow-lite-api")
}
