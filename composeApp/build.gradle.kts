import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Locale

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    // Need change firebase config
//    id("com.google.firebase.crashlytics")
//    id("com.google.gms.google-services")

}
repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.media3.exoplayer.hls)
            implementation("androidx.media3:media3-exoplayer:1.3.1")
            implementation("androidx.media3:media3-ui:1.3.1")
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.ui.android)
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.16.0"))
        }
        commonMain.dependencies {
            api("io.github.qdsfdhvh:image-loader:1.10.0")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(project(":base_sdk"))
            implementation(project(":commonUi"))
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigator
            implementation(libs.voyager.navigator)
            // Screen Model
            implementation(libs.voyager.screenmodel)
            // BottomSheetNavigator
            implementation(libs.voyager.bottom.sheet.navigator)
            // TabNavigator
            implementation(libs.voyager.tab.navigator)
            // Transitions
            implementation(libs.voyager.transitions)
            // Koin integration
//            implementation(libs.voyager.koin) // Chưa hỗ trợ wasm
            implementation("com.google.firebase:firebase-analytics")
            implementation(libs.firebase.database)
            implementation(libs.firebase.firestore)
            implementation("com.google.firebase:firebase-auth-ktx")
            implementation("com.google.code.gson:gson:2.10.1")
        }
        wasmJsMain.dependencies {
        }
    }
}
val versionMajor = 1
val versionMinor = 0
val versionPatch = 6
val minimumSdkVersion = 33

val copyApks = tasks.register("copyApks")
android {
    namespace = "com.mp.widemovie"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    android.buildFeatures.buildConfig = true
    defaultConfig {
        applicationId = "com.mp.widemovie"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = generateVersionCode()
        versionName = generateVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            applicationVariants.all {
                // Create variant-specific task that collects the APK.
                val copyApk = tasks.register<Copy>(name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }) {
                    // Copy output files from the task that produces the APK...
                    from(packageApplicationProvider)

                    // ...into a directory relative to module source root.
                    into(file(name))

                    // Filter out any metadata files, only include APKs.
                    include { it.name.endsWith(".apk") || it.name.endsWith(".aab") }
                }
                outputs.all {
                    val output = this as? BaseVariantOutputImpl
                    output?.outputFileName =
                        "WideMovies_${buildType.name}_v${generateVersionName()}.apk" //Change name later
                    setProperty("archivesBaseName", "WideMovies-v$versionName")
                }
                val deleteApks = tasks.register<Delete>(
                    "delete${
                        name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }
                    }"
                ) {
                    if (name == "deleteDebug") {
                        delete(listOf("${rootDir}/app/debug"))
                    }
                    if (name == "deleteRelease") {
                        delete(listOf("${rootDir}/app/release"))
                    }

                }
                copyApk.dependsOn(deleteApks)
                copyApks.dependsOn(copyApk)
                assembleProvider.dependsOn(copyApk)
            }
//            val projectProperties = readProperties(file("../local.properties"))
//            val admobAppId = projectProperties.getProperty("ADMOB_ID_PROD")
//            manifestPlaceholders["admobAppId"] = admobAppId
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "21"  // Đảm bảo target JVM cho Kotlin trùng với Java
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildToolsVersion = "35.0.0"
}
apply(plugin = "org.jetbrains.kotlin.kapt")
kapt {
    correctErrorTypes = true
    useBuildCache = true
}
fun generateVersionCode(): Int {
    return minimumSdkVersion * 10000000 + versionMajor * 10000 + versionMinor * 100 + versionPatch
}

fun generateVersionName(): String {
    return "${versionMajor}.${versionMinor}.${versionPatch}"
}
tasks.clean {
    delete += listOf("${rootDir}/app/release", "${rootDir}/app/debug")
}
dependencies {
    debugImplementation(compose.uiTooling)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.appcompat)

    // Koin Test features
    testImplementation(libs.koin.test)
    // Koin for JUnit 5
    testImplementation(libs.koin.test.junit5)

    // Leak canary
    debugImplementation(libs.squareup.leakcanary.android)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":base_sdk"))
//    implementation(project(":commonUI"))
    implementation(libs.androidx.runtime.livedata)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation (libs.androidx.navigation.compose)
}

