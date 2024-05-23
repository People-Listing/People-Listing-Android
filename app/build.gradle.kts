plugins {
    alias(libs.plugins.andoridApplicationPlugin)
    id("androidx.navigation.safeargs")
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.peoplelisting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.peoplelisting"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.viewmodel.lifecycle)
    implementation(libs.compose.livedata)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)

    // shimmering
    implementation(libs.facebook.shimmer)

    // swipe to refresh
    implementation(libs.rxbinding.swiperefreshlayoutt)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Dependency Injection (Kodein)
    implementation(libs.kodein.di)
    implementation(libs.kodein.di.framework.androidx)
    implementation(libs.kodein.di.framework.androidx.viewmodel)


    // Dependency Injection (Hilt)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


    // Timber
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}