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
    implementation(project(":sebase"))
    api(Libs.Retrofit2.retrofit)
    api(Libs.Retrofit2.converter_gson)
    api(Libs.Kotlin.kotlinx_coroutines_core)
    api(Libs.Kotlin.kotlinx_coroutines_android)
}
