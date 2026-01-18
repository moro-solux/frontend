import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)

}

android {
    namespace = "com.solux.moro"
    compileSdk = 36


    defaultConfig {
        applicationId = "com.solux.moro"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val props = Properties()
        val localPropsFile = rootProject.file("local.properties")
        if (localPropsFile.exists()) {
            props.load(localPropsFile.inputStream())
        }

        manifestPlaceholders["MAPS_API_KEY"] = props.getProperty("MAPS_API_KEY") ?: ""
        manifestPlaceholders["PLACES_API_KEY"] = props.getProperty("PLACES_API_KEY") ?: ""
        buildConfigField("String", "PLACES_API_KEY", "\"${props.getProperty("PLACES_API_KEY")}\"")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // Lifecycle /Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Hilt  dependencies
    implementation(libs.hilt.android)
    implementation(libs.play.services.maps)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.lifecycle.vm.compose)
    // Navigation(Compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.animation.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // 로그 확인용 라이브러리
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Google Places 라이브러리
    implementation("com.google.android.libraries.places:places:3.3.0")
    // CameraX (카메라 기능)
    val cameraVersion = "1.3.1"
    implementation("androidx.camera:camera-core:${cameraVersion}")
    implementation("androidx.camera:camera-camera2:${cameraVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraVersion}")
    implementation("androidx.camera:camera-view:${cameraVersion}")

    // GPS 결과를 코루틴으로 (.await() 함수용)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // coil (사진 파일(Uri)을 화면에 보여주기 기능)
    implementation("io.coil-kt:coil-compose:2.5.0")

    // 권한 요청 (Accompanist)
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.launchdarkly:okhttp-eventsource:3.0.0")

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    //implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:okhttp-sse:4.12.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")


}
