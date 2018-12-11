import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.gradle.internal.impldep.org.junit.platform.launcher.EngineFilter.includeEngines


description = """ `Server` module """
// group = "com.okta.developer"

plugins {
    base
    java
    application
    id("org.springframework.boot") version "2.1.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

application {
    mainClassName = "com.okta.developer.demo.DemoApplication"
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
    //implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$springBootVersion")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
    implementation("org.projectlombok:lombok:1.18.4")
    runtime("org.springframework.boot:spring-boot-devtools:$springBootVersion")
    runtime("com.h2database:h2:1.4.197")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterEngineVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterEngineVersion")
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

val frontenFolder = "../client"

tasks {
    val buildFrontend by registering(Exec::class) {
        gradleExecute("npmBuild")
    }

    val runFrontend by registering(Exec::class) {
        gradleExecute("npmRunServe")
    }

    val jar by existing {
        dependsOn(buildFrontend)
        copy {
            from(file("$frontenFolder/dist"))
            into("public")
        }
    }

    val bootJar by existing {
        finalizedBy(runFrontend)
    }

    withType<ProcessResources> {
        //TODO
    }

    getByName<Test>("test") {
        useJUnitPlatform {
            includeEngines("junit-jupiter")
            excludeEngines("junit-vintage")
        }
    }
}


fun Exec.gradleExecute(vararg commands: String) {
    val commandsList = listOf("cmd", "/c", "gradle", *commands)
    val isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
    with(this) {
        workingDir(frontenFolder)
        if (isWindows) commandLine(commandsList)
        else commandLine(commandsList.drop(2))
    }
}

