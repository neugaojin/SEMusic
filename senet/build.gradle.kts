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
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    api("com.squareup.retrofit2:retrofit:2.4.0")
    api("com.squareup.retrofit2:converter-gson:2.4.0")
    api("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    api("io.github.microutils:kotlin-logging:1.6.24")
}

repositories {
    mavenCentral()
}
