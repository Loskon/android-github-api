@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(deps.plugins.androidLibrary)
    alias(deps.plugins.kotlin)
    alias(deps.plugins.kapt)
}

android {
    namespace = "com.loskon.network"
    compileSdk = deps.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = deps.versions.minSdk.get().toInt()

        buildConfigField("String", "API_BASE_URL", "\"https://api.github.com/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    // Desugar
    coreLibraryDesugaring(deps.desugar)
    // Kotlin
    implementation(deps.core)
    // Network
    implementation(deps.bundles.retrofitMoshi)
    implementation(deps.moshi)
    kapt(deps.moshiCodegen)
    // ImageLoader
    implementation(deps.coil)
    // DI
    implementation(deps.koin)
    // Logs
    implementation(deps.timber)
    // Test
    testImplementation(deps.mockito)
    testImplementation(deps.junit4)
    androidTestImplementation(deps.extJunit)
    androidTestImplementation(deps.espresso)
}