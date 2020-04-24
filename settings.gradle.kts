rootProject.name = "SEMusic"
include(":app"
        , ":senet"
        , ":service"
        , ":sebase"
        , ":ffmpeg")
project(":senet").projectDir = File("$rootDir/module/senet")
project(":service").projectDir = File("$rootDir/module/service")
project(":sebase").projectDir = File("$rootDir/module/sebase")
project(":ffmpeg").projectDir = File("$rootDir/module/ffmpeg")

//apply("gradle/exo_player.gradle")