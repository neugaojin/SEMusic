import com.google.gson.Gson

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.google.code.gson:gson:2.8.6")
    }
}
rootProject.name = "SEMusic"

data class Module(val name: String, val path: String)

val modules = Gson().fromJson(File("$rootDir/config/modules.json").readText(), Array<Module>::class.java)!!
modules.forEach {
    include(it.name)
    project(it.name).projectDir = File("$rootDir/${it.path}")
}
//apply("gradle/exo_player.gradle")