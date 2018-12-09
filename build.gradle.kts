description = """ parent/root module """

plugins {
    java
    base
}

allprojects {
    group = "ru.gazprombank.cache"
    version = "1.0"
    repositories {
        gradlePluginPortal()
    }
}

subprojects { tasks { } }

dependencies {
    subprojects.forEach { archives(it) }
}
