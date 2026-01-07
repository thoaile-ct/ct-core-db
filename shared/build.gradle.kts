import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

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
    publications {
        // Create a publication named "release" targeting the Android library's release variant
        create<MavenPublication>("release") {
            groupId = "com.carousell"
            artifactId = "ct-android-core-db"
            version = libVersion // is the version defined into file version.properties

            // Publish the Android AAR produced by the release variant directly
            val aar = layout.buildDirectory.file("outputs/aar/shared-release.aar")
            artifact(aar)

            // Optionally attach sources jar if available
            // artifact(tasks.named("androidReleaseSourcesJar"))
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            // Replace with your target GitHub Packages repo URL
            url = uri("https://maven.pkg.github.com/carousell/ct-android-core-db")
            credentials {
                // Ideally sourced from environment variables or Gradle properties
                username = System.getenv("PACKAGES_USERNAME_GITHUB") ?: ""
                password = System.getenv("PACKAGES_TOKEN_GITHUB") ?: ""
            }
        }
    }
}

// Load version from version.properties at the root
val versionPropsFile = rootProject.file("version.properties")
val versionProps = Properties().apply {
    if (versionPropsFile.exists()) {
        load(versionPropsFile.inputStream())
    }
}

// version from version.properties
val libVersion = listOf(
    versionProps.getProperty("major") ?: "0",
    versionProps.getProperty("minor") ?: "0",
    versionProps.getProperty("patch") ?: "0"
).joinToString(".")


