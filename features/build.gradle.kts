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

    composeOptions {
        kotlinCompilerExtensionVersion = deps.versions.compose.get()
    }

    kotlinOptions {
        jvmTarget = "18"
        freeCompilerArgs = listOf (
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
        )
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    // Module
    implementation(projects.network)
    implementation(projects.base)
    // Desugar
    coreLibraryDesugaring(deps.desugar)
    // Compose
    implementation(deps.composeUi)
    implementation(deps.composeUiToolingPreview)
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
    // Android Compose
    implementation(deps.accompanistSystemuicontroller)
    implementation(deps.activityCompose)
    implementation(deps.materialCompose)
    implementation(deps.constraintlayoutCompose)
    implementation(deps.runtimeCompose)
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