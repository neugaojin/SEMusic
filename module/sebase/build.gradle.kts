plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
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
                abiFilters("armeabi-v7a")
            }
        }
    }
}

dependencies {
    val kotlinVersion: String by project
    val corcoutinesVersion: String by project
    val androidxVersion: String by project
    api("androidx.appcompat:appcompat:$androidxVersion")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$corcoutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$corcoutinesVersion")
}

repositories {
    mavenCentral()
}
