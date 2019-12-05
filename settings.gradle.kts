rootProject.name = "SEMusic"
include(":app", ":senet", ":service", ":router", ":sebase")
project(":senet").projectDir = File("$rootDir/module/senet")
project(":service").projectDir = File("$rootDir/module/service")
project(":router").projectDir = File("$rootDir/module/router")
project(":sebase").projectDir = File("$rootDir/module/sebase")