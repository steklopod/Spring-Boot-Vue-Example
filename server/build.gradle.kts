import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.gradle.internal.impldep.org.junit.platform.launcher.EngineFilter.includeEngines

description = """ `Server` module """

plugins {
    base
    java
    application
    id("org.springframework.boot") version "2.1.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

application {
    mainClassName = "ru.steklopod.App"
}

buildscript {
    repositories {
        jcenter()
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin")
    }
}

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots/") }
}

val junitJupiterEngineVersion = "5.3.0"
val springBootVersion = "2.1.0.RELEASE"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.projectlombok:lombok:1.18.4")

    runtime("org.springframework.boot:spring-boot-devtools:$springBootVersion")
    runtime("com.h2database:h2:1.4.197")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterEngineVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterEngineVersion")
//    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$springBootVersion")

}

configure<JavaPluginConvention> {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

apply(from = "serverTasks.gradle.kts")