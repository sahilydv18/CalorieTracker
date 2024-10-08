import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.calorietracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.calorietracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        file("../local.properties").inputStream().use { properties.load(it) }
        buildConfigField("String", "API_KEY", properties.getProperty("MY_API_KEY").toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // lottie
    implementation("com.airbnb.android:lottie-compose:6.5.0")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // dagger-hilt
    implementation("com.google.dagger:hilt-android:2.46")
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    kapt("com.google.dagger:hilt-android-compiler:2.46")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // work manager
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}