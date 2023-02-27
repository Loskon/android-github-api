@file:Suppress("UnstableApiUsage")

include(":features")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("deps") {
            from(files("gradle/dependencies.toml"))
        }
    }
}

rootProject.name = "GitHubApi"
include(":app")
include(":network")
include(":base")