plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.navigation)
}

android {
    namespace = "com.loskon.features"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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
    coreLibraryDesugaring(libs.desugar)
    // Module
    implementation(projects.network)
    implementation(projects.database)
    implementation(projects.base)
    // Kotlin
    implementation(libs.core)
    // Android
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.fragment)
    implementation(libs.swiperefreshlayout)
    implementation(libs.preference)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)
    // DI
    implementation(libs.koin)
    // Logs
    implementation(libs.timber)
    // Test
    testImplementation(libs.mockito)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
}