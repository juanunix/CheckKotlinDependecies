package org.example

import java.io.File

fun main() {
    val projectDir =
        File("path/to/your/project") // Cambia esto a la ruta de tu proyecto
    val gradleFiles = projectDir.walkTopDown()
        .filter { it.name == "build.gradle" || it.name == "build.gradle.kts" }

    gradleFiles.forEach { file ->
        println("Dependencies in ${file.path}:")
        printDependencies(file)
        println()
    }
}

fun printDependencies(file: File) {
    val dependenciesSection = StringBuilder()
    var inDependenciesBlock = false

    file.forEachLine { line ->
        if (line.trim().startsWith("dependencies {")) {
            inDependenciesBlock = true
        }

        if (inDependenciesBlock) {
            dependenciesSection.appendLine(line)

            if (line.trim() == "}") {
                inDependenciesBlock = false
            }
        }
    }

    val dependencies = dependenciesSection.toString().lines()
        .filter { it.trim().startsWith("implementation") || it.trim().startsWith("api") }
        .map { it.trim() }

    dependencies.forEach { dependency ->
        println(dependency)
    }
}
