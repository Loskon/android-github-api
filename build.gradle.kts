import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlin).apply(false)
    alias(libs.plugins.gms).apply(false)
    alias(libs.plugins.firebaseCrashlytics).apply(false)
    alias(libs.plugins.navigation).apply(false)
    alias(libs.plugins.parcelize).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.ktlint).apply(false)
    alias(libs.plugins.detekt).apply(false)
    alias(libs.plugins.gradlevers).apply(false)
}

subprojects {
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "checkstyle")

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    tasks.register("checkDependenciesUpdates", DependencyUpdatesTask::class) {
        group = "verification"
        description = "Inspect dependecies updates."

        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }

    tasks.register("detektCode", Detekt::class) {
        group = "verification"
        description = "Runs code checks."

        buildUponDefaultConfig = true
        config.from(files("$rootDir/configs/detekt.yml"))

        include("**/*.kt")
        include("**/*.kts")

        exclude(".*build.*")
        exclude("*/resources/.*")
        exclude(".*/tmp/.*")
        exclude("**/build/**")

        reports {
            html.required.set(true)
            html.outputLocation.set(file("$projectDir/build/reports/detekt-report.html"))
        }
    }

    tasks.register("checkstyle", Checkstyle::class) {
        group = "verification"
        description = "Runs code style checks."

        source(files("src/main/"))
        configFile = file("$rootDir/configs/checkstyle.xml")
        classpath = files()

        include("**/*.xml")
        include("**/*.kt")

        exclude("**/gen/**")
        exclude("**/R.java")
        exclude("**/BuildConfig.java")

        reports {
            html.required.set(true)
            html.outputLocation.set(file("$projectDir/build/reports/checkstyle-report.html"))
        }
    }

    tasks.register("checkBeforePushFast") {
        group = "verification"
        description = "Fast inspect code before push."
        dependsOn("checkstyle", "detektCode", "lintKotlin")
    }

    tasks.register("checkBeforePush") {
        group = "verification"
        description = "Inspect code before push."
        dependsOn("checkstyle", "detektCode", "lintKotlin", "lintDebug")
    }
}