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
    val kotlinVersion: String by project
    val corcoutinesVersion: String by project
    implementation(project(":sebase"))
    api("com.squareup.retrofit2:retrofit:2.6.2")
    api("com.squareup.retrofit2:converter-gson:2.4.0")
    api("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    api("com.squareup.retrofit2:adapter-rxjava:2.6.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$corcoutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$corcoutinesVersion")
}
