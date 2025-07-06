import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.navigation)
    alias(libs.plugins.composeCompiler)
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)
    implementation(projects.network)
    implementation(projects.database)
    implementation(projects.base)
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.fragment)
    implementation(libs.swiperefreshlayout)
    implementation(libs.preference)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.coil)
    implementation(libs.koin)
    implementation(libs.timber)
    implementation(libs.compose)
    implementation(libs.composePreview)
    implementation(libs.composeMaterial2)
    implementation(libs.composeConstraint)
    testImplementation(libs.mockito)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
}