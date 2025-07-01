import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)
    implementation(projects.base)
    implementation(projects.features)
    implementation(projects.network)
    implementation(projects.database)
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseCrashlytics)
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.bundles.navigation)
    implementation(libs.koin)
    implementation(libs.splashscreen)
    implementation(libs.timber)
    testImplementation(libs.mockito)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
}