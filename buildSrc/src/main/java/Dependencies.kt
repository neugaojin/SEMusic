/**
 *Author: gaojin
 *Time: 2020/9/24 3:29 PM
 */
object Versions {
    const val kotlin_version = "1.4.0"
    const val kotlin_gradle_plugin_version = "1.4.10"
    const val kotlin_corcoutine_version = "1.3.9"

    const val app_compat = "1.2.0"
    const val material = "1.3.0-alpha02"
    const val startup_runtime = "1.0.0-beta01"
    const val aac_version = "2.2.0"
    const val lifecycle_ktx_version = "2.2.0"
    const val scene_version = "1.+"
    const val glide_version = "4.8.0"
    const val gson_version = "2.8.5"
    const val kotlin_logging = "1.6.24"
    const val rx_android_version = "2.1.1"
    const val rx_java_version = "2.2.9"
    const val banner_version = "1.4.10"
    const val exoplayer_version = "2.11.4"
    const val paging_runtime = "3.0.0-alpha06"
    const val media_version = "1.1.0"
    const val junit_version = "4.13"
    const val retrofit_version = "2.9.0"
    const val constraintlayout = "2.0.2"
    const val okhttp_logging_interceptor = "3.9.0"
}

object Libs {
    const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val startup_runtime = "androidx.startup:startup-runtime:${Versions.startup_runtime}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
    const val gson = "com.google.code.gson:gson:${Versions.gson_version}"
    const val kotlin_logging = "io.github.microutils:kotlin-logging:${Versions.kotlin_logging}"
    const val banner = "com.youth.banner:banner:${Versions.banner_version}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"

    object Kotlin {
        const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
        const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin_version}"
        const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"
        const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_corcoutine_version}"
        const val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_corcoutine_version}"
    }

    object Jetpack {
        const val lifecycle_runtime = "android.arch.lifecycle:runtime:${Versions.aac_version}"
        const val lifecycle_extensions = "android.arch.lifecycle:extensions:${Versions.aac_version}"
        const val lifecycle_compiler = "android.arch.lifecycle:compiler:${Versions.aac_version}"
        const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_ktx_version}"
        const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_ktx_version}"
        const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_ktx_version}"
        const val lifecycle_viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle_ktx_version}"

        const val paging_runtime = "androidx.paging:paging-runtime:${Versions.paging_runtime}"
        const val room_runtime = "android.arch.persistence.room:runtime:${Versions.aac_version}"
        const val room_compiler = "android.arch.persistence.room:compiler:${Versions.aac_version}"

    }

    object Scene {
        const val scene = "com.bytedance.scene:scene:${Versions.scene_version}"
        const val scene_ui = "com.bytedance.scene:scene-ui:${Versions.scene_version}"
        const val scene_shared_element_animation = "com.bytedance.scene:scene-shared-element-animation:${Versions.scene_version}"
        const val scene_ktx = "com.bytedance.scene:scene-ktx:${Versions.scene_version}"
    }

    object RxJava {
        const val rx_android = "io.reactivex.rxjava2:rxandroid:${Versions.rx_android_version}"
        const val rx_java = "io.reactivex.rxjava2:rxjava:${Versions.rx_java_version}"
    }

    object ExoPlayer {
        const val core = "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayer_version}"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayer_version}"
        const val mediasession = "com.google.android.exoplayer:extension-mediasession:${Versions.exoplayer_version}"
        const val media = "androidx.media:media:${Versions.media_version}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit_version}"
        const val runner = "com.android.support.test:runner:1.0.2"
        const val espresso_core = "com.android.support.test.espresso:espresso-core:3.0.2"
    }

    object Retrofit2 {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
        const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    }
}