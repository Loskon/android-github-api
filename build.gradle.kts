plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlin).apply(false)
    alias(libs.plugins.gms).apply(false)
    alias(libs.plugins.firebaseCrashlytics).apply(false)
    alias(libs.plugins.kapt).apply(false)
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

    tasks.register("ktlint", org.jmailen.gradle.kotlinter.tasks.LintTask::class) {
        group = "verification"
        description = "Check Kotlin code style."

        source(files("src"))
    }

    tasks.register("detektCode", io.gitlab.arturbosch.detekt.Detekt::class) {
        group = "verification"
        description = "Runs code checks."

        buildUponDefaultConfig = true
        setSource(files("$projectDir/src/main/"))
        config.from(files("$rootDir/configs/detekt-config.yml"))

        include("**/*.kt")
        include("**/*.kts")

        exclude(".*test.*")
        exclude(".*build.*")
        exclude("*/resources/.*")
        exclude(".*/tmp/.*")
        exclude("**/build/**")
        exclude("**/test/**")
        exclude(".*Test.kt")
        exclude(".*Spec.kt")

        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("$projectDir/build/reports/detekt/detekt-report.xml"))
            html.required.set(true)
            html.outputLocation.set(file("$projectDir/build/reports/detekt/detekt-report.html"))
        }
    }

    tasks.register("checkstyle", Checkstyle::class) {
        group = "verification"
        description = "Runs code style checks."

        source(files("src/main/"))
        configFile = file("$rootDir/configs/checkstyle-config.xml")
        classpath = files()

        include("**/*.xml")
        include("**/*.kt")

        exclude("**/gen/**")
        exclude("**/R.java")
        exclude("**/BuildConfig.java")

        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("$projectDir/build/reports/checkstyle/checkstyle-report.xml"))
            html.required.set(true)
            html.outputLocation.set(file("$projectDir/build/reports/checkstyle/checkstyle-report.html"))
        }
    }

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    tasks.register("checkDependenciesUpdates", com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
        group = "verification"
        description = "Inspect dependecies updates."

        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }

    tasks.register("checkBeforePushFast") {
        group = "verification"
        description = "Inspect code before push"
        dependsOn("checkstyle", "detektCode", "ktlint")
    }

    tasks.register("checkBeforePush") {
        group = "verification"
        description = "Inspect code before push"
        dependsOn("checkstyle", "detektCode", "ktlint", "lintDebug")
    }
}