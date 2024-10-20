plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id ("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.mertg.inst2convert"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mertg.inst2convert"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.cronet.embedded)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

/*    // Dependency Injection - Hilt
    implementation( "com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-compiler:2.44")

    // Retrofit & Gson
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // OkHttp (for logging, optional)
    implementation (libs.logging.interceptor)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Navigation Component
    implementation( libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Material Design (for UI components like buttons, text fields, etc.)
    implementation (libs.material.v140)*/


    implementation ("com.google.android.material:material:1.9.0")
    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

/*
    // Firebase Auth and Firestore
    implementation( "com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.firebase:firebase-analytics")
*/

    implementation ("androidx.fragment:fragment:1.5.7") // Veya en güncel sürüm

    // Dagger-Hilt for Dependency Injection
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt ("com.google.dagger:hilt-compiler:2.48")

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // Coroutines
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit for API calls
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (for logging, optional)
    implementation (libs.logging.interceptor)

    // Navigation Component
    implementation( libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Glide for Image Loading
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    // Fragment KTX for viewModels()
    implementation ("androidx.fragment:fragment-ktx:1.6.1")

    // HTTP Library
    implementation("com.squareup.okhttp3:okhttp:4.9.3")


}