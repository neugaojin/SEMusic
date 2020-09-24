plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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
                abiFilters("armeabi-v7a", "arm64-v8a")
            }
        }
    }
}

dependencies {
    implementation(project(":sebase"))
    implementation(project(":ffmpeg"))
    implementation(Libs.glide)
    kapt(Libs.glide_compiler)
    api(Libs.ExoPlayer.media)
    api(Libs.gson)
    api(Libs.ExoPlayer.core)
    api(Libs.ExoPlayer.ui)
    api(Libs.ExoPlayer.mediasession)
}

repositories {
    mavenCentral()
}
