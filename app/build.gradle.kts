plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.dorongold.task-tree") version "1.4"
//    id("viti")
}

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

    sourceSets["main"].java.srcDirs("src/main/kotlin")
    sourceSets["test"].java.srcDirs("src/test/kotlin")
    sourceSets["androidTest"].java.srcDirs("src/androidTest/kotlin")

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

        disable("MissingTranslation", "ExtraTranslation")

        isCheckGeneratedSources = false
    }
}

dependencies {
    val androidxVersion: String by project
    val kotlinVersion: String by project
    val aacVersion: String by project
    val sceneVersion: String by project
    val corcoutinesVersion: String by project
    val androidxMediaVersion: String by project
    val glideVersion: String by project
    val gsonVersion: String by project
    val exoplayerVersion: String by project

    implementation(project(":senet"))
    implementation(project(":service"))
    implementation(project(":sebase"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("androidx.appcompat:appcompat:$androidxVersion")
    implementation("androidx.core:core-ktx:$androidxVersion")
    implementation("com.google.android.material:material:1.3.0-alpha02")
    implementation("androidx.startup:startup-runtime:1.0.0-beta01")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$corcoutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$corcoutinesVersion")

    //****************  LifeCycle  ****************
    //For Lifecycles, LiveData, and ViewModel
    implementation("android.arch.lifecycle:runtime:$aacVersion")
    implementation("android.arch.lifecycle:extensions:$aacVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-alpha07")
    kapt("android.arch.lifecycle:compiler:$aacVersion")
    //For Room
    implementation("android.arch.persistence.room:runtime:$aacVersion")
    kapt("android.arch.persistence.room:compiler:$aacVersion")
    //****************  LifeCycle  ****************

    //****************  scene  ****************
    implementation("com.bytedance.scene:scene:$sceneVersion")
    implementation("com.bytedance.scene:scene-ui:$sceneVersion")
    implementation("com.bytedance.scene:scene-shared-element-animation:$sceneVersion")
    implementation("com.bytedance.scene:scene-ktx:$sceneVersion")

    //第三方
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("com.youth.banner:banner:1.4.10")
    implementation("io.github.microutils:kotlin-logging:1.6.24")
    implementation("com.google.code.gson:gson:$gsonVersion")

    implementation("androidx.media:media:$androidxMediaVersion")
    implementation("com.google.android.exoplayer:exoplayer-core:$exoplayerVersion")
    implementation("com.google.android.exoplayer:exoplayer-ui:$exoplayerVersion")
    implementation("com.google.android.exoplayer:extension-mediasession:$exoplayerVersion")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}

androidExtensions {
    isExperimental = true
}