@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(deps.plugins.androidApplication)
    alias(deps.plugins.kotlin)
    alias(deps.plugins.navigation)
}

android {
    namespace = "com.loskon.githubapi"
    compileSdk = deps.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.loskon.githubapi"

        minSdk = deps.versions.minSdk.get().toInt()
        targetSdk = deps.versions.targetSdk.get().toInt()

        versionCode = deps.versions.debugVersionCode.get().toInt()
        versionName = deps.versions.debugVersionName.get()

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
}

dependencies {
    // Module
    implementation(projects.network)
    implementation(projects.base)
    implementation(projects.features)
    // Desugar
    coreLibraryDesugaring(deps.desugar)
    // Kotlin
    implementation(deps.core)
    // Android
    implementation(deps.appcompat)
    implementation(deps.material)
    implementation(deps.activity)
    implementation(deps.constraintlayout)
    implementation(deps.fragment)
    implementation(deps.bundles.navigation)
    // DI
    implementation(deps.koin)
    // Misc
    implementation(deps.splashscreen)
    // Logs
    implementation(deps.timber)
    // Test
    testImplementation(deps.mockito)
    testImplementation(deps.junit4)
    androidTestImplementation(deps.extJunit)
    androidTestImplementation(deps.espresso)
}