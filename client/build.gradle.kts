import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.github.node-gradle.node") version Versions.nodeJSGradlePlugin
    id("idea")
}

group = "com.epam.esm"
version = "0.0.1-SNAPSHOT"

node {
    version.set(Versions.nodeJS)
    download.set(true)
}

idea {
    module {
        excludeDirs.addAll(
            listOf(
                file("$projectDir/node_modules"),
                file("$projectDir/dist"),
            )
        )
    }
}

tasks {
    register<NpmTask>("build") {
        dependsOn(npmInstall)
        group = "build"
        description = "Builds production version of the app client"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "build"))
    }
}