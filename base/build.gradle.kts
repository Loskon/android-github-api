@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(deps.plugins.androidLibrary)
    alias(deps.plugins.kotlin)
}

android {
    namespace = "com.loskon.base"
    compileSdk = deps.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = deps.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Desugar
    coreLibraryDesugaring(deps.desugar)
    // Kotlin
    implementation(deps.core)
    // Android
    implementation(deps.appcompat)
    implementation(deps.material)
    implementation(deps.constraintlayout)
    implementation(deps.swiperefreshlayout)
    implementation(deps.preference)
    implementation(deps.bundles.lifecycle)
    // Logs
    implementation(deps.timber)
    // Test
    testImplementation(deps.mockito)
    testImplementation(deps.junit4)
    androidTestImplementation(deps.extJunit)
    androidTestImplementation(deps.espresso)
}