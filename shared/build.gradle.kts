import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.publish.maven.MavenPublication

// Use version from root (loaded in root build.gradle.kts)
val libVersion = rootProject.extra["libVersion"] as String

// Set project coordinates so all publications inherit them
group = "com.carousell"
version = libVersion

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)

    // Apply Maven Publish plugin for publishing artifacts
    id("maven-publish")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here

            // Room DB
            implementation(libs.room.runtime)

            implementation(libs.sqlite.bundled)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.chotot.vn.core_db.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    // Enable publishing of the release variant for maven-publish
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/carousell/ct-android-core-db")
            credentials {
                username = System.getenv("PACKAGES_USERNAME_GITHUB") ?: ""
                password = System.getenv("PACKAGES_TOKEN_GITHUB") ?: ""
            }
        }
        mavenLocal()
    }

    // Note: In KMP projects, android.publishing.singleVariant("release") doesn't create a publication
    // We need to manually create an Android publication or use the kotlinMultiplatform publication
}

afterEvaluate {
    // Debug: Create a task to print publications
    tasks.register("printPublications") {
        doLast {
            println("=== Available Publications ===")
            publishing.publications.forEach {
                println("Publication: ${it.name}")
                if (it is MavenPublication) {
                    println("  Group: ${it.groupId}")
                    println("  Artifact: ${it.artifactId}")
                    println("  Version: ${it.version}")
                }
            }
            println("\n=== Publish Tasks ===")
            tasks.matching { it.name.startsWith("publish") && it.name.contains("Publication") }.forEach {
                println("Task: ${it.name}")
            }
        }
    }

    // Configure all publications with proper coordinates
    publishing.publications.configureEach {
        if (this is MavenPublication) {
            when (name) {
                "kotlinMultiplatform" -> {
                    // This is the main publication we want to publish
                    groupId = group.toString()
                    artifactId = "ct-android-core-db"
                    version = libVersion
                }
                "iosArm64", "iosSimulatorArm64" -> {
                    // Keep default names for iOS (they won't be published)
                }
            }
        }
    }

    // Create custom tasks to publish only the kotlinMultiplatform publication (avoiding iOS)
    tasks.register("publishAndroidToGitHub") {
        group = "publishing"
        description = "Publishes the Kotlin Multiplatform publication (Android library) to GitHub Packages"
        dependsOn(tasks.named("publishKotlinMultiplatformPublicationToGitHubPackagesRepository"))
    }

    tasks.register("publishAndroidToMavenLocal") {
        group = "publishing"
        description = "Publishes the Kotlin Multiplatform publication (Android library) to Maven Local"
        dependsOn(tasks.named("publishKotlinMultiplatformPublicationToMavenLocal"))
    }
}








