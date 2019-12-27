rootProject.name = "SEMusic"
include(":app", ":senet", ":service", ":sebase")
project(":senet").projectDir = File("$rootDir/module/senet")
project(":service").projectDir = File("$rootDir/module/service")
project(":sebase").projectDir = File("$rootDir/module/sebase")