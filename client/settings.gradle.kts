rootProject.name = "client"

 pluginManagement {
     repositories {
         gradlePluginPortal()
         jcenter()
     }
     resolutionStrategy {
         eachPlugin {
             if (requested.id.id == "kotlin2js") {
                 useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
             }
//                if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
//                 useVersion(gradle.rootProject.extra["kotlin.version"] as String)
//             }
        
         }
     }
}
