plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.loskon.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        buildConfigField("String", "API_BASE_URL", "\"https://api.github.com/\"")
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
    coreLibraryDesugaring(libs.desugar)
    implementation(libs.core)
    implementation(libs.bundles.retrofitMoshi)
    implementation(libs.moshi)
    implementation(libs.coil)
    implementation(libs.paging3)
    implementation(libs.koin)
    implementation(libs.timber)
    ksp(libs.moshiCodegen)
    testImplementation(libs.mockito)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
}