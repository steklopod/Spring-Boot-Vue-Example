rootProject.name = "boot-vue"

include("server", "client")

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}