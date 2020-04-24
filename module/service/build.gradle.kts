plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply("$rootDir/gradle/common.kts")

android {

    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
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
    val androidxMediaVersion: String by project
    val glideVersion: String by project
    val gsonVersion: String by project
    val exoplayerVersion: String by project
    implementation(project(":sebase"))
    implementation(project(":ffmpeg"))
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")
    api("androidx.media:media:$androidxMediaVersion")
    api("com.google.code.gson:gson:$gsonVersion")
    api("com.google.android.exoplayer:exoplayer-core:$exoplayerVersion")
    api("com.google.android.exoplayer:exoplayer-ui:$exoplayerVersion")
    api("com.google.android.exoplayer:extension-mediasession:$exoplayerVersion")
}

repositories {
    mavenCentral()
}
