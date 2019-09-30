plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.dorongold.task-tree") version "1.4"
    id("io.gitlab.arturbosch.detekt").version("1.0.0-RC16")
}

android {

    compileSdkVersion(28)

    defaultConfig {
        applicationId = "com.se.music"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
    }

    dataBinding.isEnabled = true

    sourceSets["main"].java.srcDirs("src/main/kotlin", "src/main/aidl")

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            ndk {
                abiFilters("armeabi-v7a")
            }
        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            ndk {
                abiFilters("armeabi-v7a", "arm64-v8a", "x86")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        // true--关闭lint报告的分析进度
        isQuiet = true
        // true--错误发生后停止gradle构建
        isAbortOnError = false
        // true--只报告error
        isIgnoreWarnings = true
        // true--忽略有错误的文件的全/绝对路径(默认是true)
        isCheckAllWarnings = false
        // true--所有warning当做error
        isWarningsAsErrors = false
        // true--error输出文件不包含源码行号
        isNoLines = true
        // true--显示错误的所有发生位置，不截取
        isShowAll = true
        htmlReport = true

        setHtmlOutput(project.rootProject.file("build${File.separator}reports${File.separator}lint${File.separator}lint-report.html"))
        isCheckGeneratedSources = false
    }
}

dependencies {
    val kotlinVersion: String by project
    val aacVersion: String by project

    implementation(project(":senet"))
    implementation(project(":service"))
    implementation(project(":router"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.1.0-alpha10")
    implementation("androidx.core:core-ktx:1.1.0")

    //****************  LifeCycle  ****************
    //For Lifecycles, LiveData, and ViewModel
    implementation("android.arch.lifecycle:runtime:$aacVersion")
    implementation("android.arch.lifecycle:extensions:$aacVersion")
    kapt("android.arch.lifecycle:compiler:$aacVersion")
    //For Room
    implementation("android.arch.persistence.room:runtime:$aacVersion")
    kapt("android.arch.persistence.room:compiler:$aacVersion")
    //****************  LifeCycle  ****************

    //第三方
    implementation("com.github.bumptech.glide:glide:4.8.0")
    kapt("com.github.bumptech.glide:compiler:4.8.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("com.youth.banner:banner:1.4.10")
    implementation("io.github.microutils:kotlin-logging:1.6.24")
}

androidExtensions {
    isExperimental = true
}

detekt {
    toolVersion = "1.0.0-RC16"
    config = files("${rootProject.projectDir}/config.yml")
    input = files("src/main/kotlin")
}
