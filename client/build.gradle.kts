import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.task.NodeTask

description = """ `client` module (frontend, vue.js) """

plugins {
    id("com.moowork.node") version "1.2.0"
}

buildscript {
    repositories {
        jcenter()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11")
    }
}

repositories { jcenter() }
dependencies { }

tasks {
    val inputVueJsCompiledFolder = "/target/dist"
    val outBackendDir = "/build/resources/custom-ui/"

    val dist by registering(Copy::class) {
        group = "Distribut"
        description = """ Makes copy of $inputVueJsCompiledFolder folder into $outBackendDir of `server` module. """
        from(file(inputVueJsCompiledFolder))
        into(file("../server/$outBackendDir"))
        doLast { println(">>> Ok. Frontend files from $inputVueJsCompiledFolder have copied.<<<") }
    }

    create<NpmTask>("npmBuild") {
        group = "Node"
        description = " Run commands: ``npm run build` & `npm run build` "
        dependsOn("npmInstall")
        setArgs(listOf("run", "build"))
        finalizedBy(dist)
        doLast { println(">>> Ok. Frontend `run build` is done.") }
    }
}

node { download = false } //TODO (true - if need)
