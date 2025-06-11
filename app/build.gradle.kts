plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.navigation)
}

android {
    namespace = "com.loskon.githubapi"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.loskon.githubapi"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

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
    coreLibraryDesugaring(libs.desugar)
    // Module
    implementation(projects.network)
    implementation(projects.base)
    implementation(projects.features)
    implementation(projects.database)
    // Firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseCrashlytics)
    // Kotlin
    implementation(libs.core)
    // Android
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.bundles.navigation)
    // DI
    implementation(libs.koin)
    // Misc
    implementation(libs.splashscreen)
    // Logs
    implementation(libs.timber)
    // Test
    testImplementation(libs.mockito)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
}