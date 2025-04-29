plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    // Add the dependency for the Crashlytics Gradle plugin
//    id("com.google.firebase.crashlytics") version "3.0.3" apply false
//    id("com.google.gms.google-services") version "4.4.2" apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
}