
description = """ `Backend` tasks """

val frontenFolder = "../client"

tasks {
    val installFrontend by registering(Exec::class) {
        group = "Frontend"
        npmExecute("npm_install")
    }

    val buildFrontend by registering(Exec::class) {
        group = "Frontend"
        npmExecute("npmBuild")
    }

    val runFrontend by registering(Exec::class) {
        group = "Frontend"
        npmExecute("npmRunServe")
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


fun Exec.npmExecute(vararg commands: String) {
    val commandsList = listOf("cmd", "/c", "gradle", *commands)
    val isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
    with(this) {
        workingDir(frontenFolder)
        if (isWindows) commandLine(commandsList)
        else commandLine(commandsList.drop(2))
    }
}

