description = """ parent """

plugins {
//    java
    base
}

allprojects {
//    group = "ru.steklopod"
    version = "1.0"
    repositories {
        gradlePluginPortal()
    }
}

subprojects { tasks { } }

dependencies {
    subprojects.forEach { archives(it) }
}