description = """ parent """

plugins {
    base
}

allprojects {
    group = "ru.steklopod"
    repositories { gradlePluginPortal() }
}

subprojects { tasks { } }

dependencies {
    subprojects.forEach { archives(it) }
}