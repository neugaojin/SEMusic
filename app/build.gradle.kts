plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.dorongold.task-tree") version "1.4"
}

apply("$rootDir/gradle/common.kts")

android {

    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.se.music"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "commitId", "\"123\"")
    }

    dataBinding.isEnabled = false

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            ndk {
                abiFilters("arm64-v8a")
            }
            buildConfigField("String", "type", "\"release\"")
        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            ndk {
                abiFilters("arm64-v8a", "x86")
            }
            buildConfigField("String", "type", "\"debug\"")
        }
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

        disable("MissingTranslation", "ExtraTranslation")

        isCheckGeneratedSources = false
    }
}

dependencies {
    implementation(project(":senet"))
    implementation(project(":service"))
    implementation(project(":sebase"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.Kotlin.kotlin_stdlib) {
        isForce = true
    }
    implementation(Libs.Kotlin.kotlin_reflect) {
        isForce = true
    }
    implementation(Libs.Kotlin.kotlin_stdlib_jdk8) {
        isForce = true
    }
    implementation(Libs.Kotlin.kotlinx_coroutines_core) {
        isForce = true
    }
    implementation(Libs.Kotlin.kotlinx_coroutines_android) {
        isForce = true
    }

    implementation(Libs.app_compat)
    implementation(Libs.material)
    implementation(Libs.startup_runtime)

    //****************  LifeCycle  ****************
    implementation(Libs.Jetpack.lifecycle_runtime)
    implementation(Libs.Jetpack.lifecycle_extensions)
    implementation(Libs.Jetpack.lifecycle_viewmodel_ktx)
    implementation(Libs.Jetpack.lifecycle_livedata_ktx)
    implementation(Libs.Jetpack.lifecycle_runtime_ktx)
    implementation(Libs.Jetpack.lifecycle_viewmodel_savedstate)
    kapt(Libs.Jetpack.lifecycle_compiler)
    //****************  LifeCycle  ****************

    //****************  scene  ****************
    implementation(Libs.Scene.scene)
    implementation(Libs.Scene.scene_ui)
    implementation(Libs.Scene.scene_ktx)
    implementation(Libs.Scene.scene_shared_element_animation)

    //第三方
    implementation(Libs.glide)
    kapt(Libs.glide_compiler)
    implementation(Libs.RxJava.rx_android)
    implementation(Libs.RxJava.rx_java)
    implementation(Libs.banner)
    implementation(Libs.okhttp_logging_interceptor)
    implementation(Libs.kotlin_logging)
    implementation(Libs.gson)
    implementation(Libs.constraintlayout)

    implementation(Libs.ExoPlayer.media)
    implementation(Libs.ExoPlayer.core)
    implementation(Libs.ExoPlayer.ui)
    implementation(Libs.ExoPlayer.mediasession)

    implementation(Libs.Jetpack.paging_runtime)
    implementation(Libs.Jetpack.room_runtime)
    kapt(Libs.Jetpack.room_compiler)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.runner)
    androidTestImplementation(Libs.Test.espresso_core)
}

androidExtensions {
    isExperimental = true
}