@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(deps.plugins.androidLibrary)
    alias(deps.plugins.kotlin)
    alias(deps.plugins.parcelize)
    alias(deps.plugins.navigation)
}

android {
    namespace = "com.loskon.features"
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
    // Module
    implementation(projects.network)
    implementation(projects.database)
    implementation(projects.base)
    // Kotlin
    implementation(deps.core)
    // Android
    implementation(deps.appcompat)
    implementation(deps.material)
    implementation(deps.constraintlayout)
    implementation(deps.recyclerview)
    implementation(deps.fragment)
    implementation(deps.swiperefreshlayout)
    implementation(deps.preference)
    implementation(deps.bundles.lifecycle)
    implementation(deps.bundles.navigation)
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