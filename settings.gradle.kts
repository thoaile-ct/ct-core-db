rootProject.name = "ct-core-db"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.github.com/carousell/ct-android-core_data") {
            credentials {
                // Provide either environment variables or hardcoded credentials (not recommended)
                username = System.getenv("PACKAGES_USERNAME_GITHUB") ?: "GITHUB_USERNAME"
                password = System.getenv("PACKAGES_TOKEN_GITHUB") ?: "YOUR_GITHUB_PAT_TOKEN"
            }
        }
    }
}

include(":composeApp")
include(":shared")