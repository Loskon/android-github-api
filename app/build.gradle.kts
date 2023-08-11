@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(deps.plugins.androidApplication)
    alias(deps.plugins.kotlin)
    alias(deps.plugins.gms)
    alias(deps.plugins.firebaseCrashlytics)
    alias(deps.plugins.navigation)
}

android {
    namespace = "com.loskon.githubapi"
    compileSdk = deps.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.loskon.githubapi"
        compileSdkPreview = "UpsideDownCake"

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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Desugar
    coreLibraryDesugaring(deps.desugar)
    // Module
    implementation(projects.network)
    implementation(projects.base)
    implementation(projects.features)
    implementation(projects.database)
    // Firebase
    implementation(platform(deps.firebaseBom))
    implementation(deps.firebaseAnalytics)
    implementation(deps.firebaseCrashlytics)
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