import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.task.NodeTask
import com.sun.deploy.config.JREInfo.setArgs

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

node { download = false } //TODO ( true - если не установлен npm )

tasks {
    val serverFolder = "../server"

    val npmRunServe by registering(Exec::class) {
        group = "Npm"
        npmExecute("run", "serve")
        doLast { println(">>> `npm INSTALL` is done ") }
    }

    val npmBuild by registering(Exec::class) {
        group = "Npm"
        npmExecute("run", "build")
        doLast { println(">>> `npm run BUILD` is done.") }
    }

    val npmInstall by existing(NpmTask::class) {
        finalizedBy(npmBuild)
        doLast { println(">>> `npm INSTALL` is done ") }
    }

    create("magic") {
        group = "Build"
        dependsOn(npmInstall)
        finalizedBy(npmRunServe)
        doLast { println(">>> `npm INSTALL + BUIlD + RUN` is done ") }
    }

    val clean by registering(Delete::class) {
        group = "Npm"
        delete("dist"); delete("node_modules"); delete("package-lock.json")
        doLast { println(">>> Ok. Cleared <<<") }
    }

    val copyToServerPublic by registering(Copy::class) {
        group = "Dist"
        from(file("dist"))
        into("$serverFolder/public")
        doLast { println(">>> Frontend dist folder succesfully copied. ") }
    }

}


fun Exec.npmExecute(vararg commands: String) {
    val commandsList = listOf("cmd", "/c", "npm", *commands)
    val isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
    with(this) {
        if (isWindows) commandLine(commandsList)
        else commandLine(commandsList.drop(2))
    }
}