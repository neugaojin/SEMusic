plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

apply("$rootDir/gradle/common.kts")

android {

    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            ndk {
                abiFilters("armeabi-v7a")
            }
        }
    }
}

dependencies {
    api(Libs.app_compat)
    api(Libs.Kotlin.kotlin_stdlib_jdk8)
    api(Libs.Kotlin.kotlinx_coroutines_core)
    api(Libs.Kotlin.kotlinx_coroutines_android)
}

repositories {
    mavenCentral()
}
