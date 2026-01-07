import java.util.Properties

// Load version from version.properties and set project coordinates
val versionPropsFile = rootProject.file("version.properties")
val versionProps = Properties().apply {
    if (versionPropsFile.exists()) {
        load(versionPropsFile.inputStream())
    }
}
val libVersion = listOf(
    versionProps.getProperty("major") ?: "0",
    versionProps.getProperty("minor") ?: "0",
    versionProps.getProperty("patch") ?: "0"
).joinToString(".")

group = "com.carousell"
version = libVersion
extra["libVersion"] = libVersion

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}
