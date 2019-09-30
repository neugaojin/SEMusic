rootProject.name = "SEMusic"
include(":app", ":senet", ":service", ":router")
project(":senet").projectDir = File("$rootDir/module/senet")
project(":service").projectDir = File("$rootDir/module/service")
project(":router").projectDir = File("$rootDir/module/router")