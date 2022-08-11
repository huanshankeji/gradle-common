package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class SourceFile(val filePath: String, val content: String)

fun Project.generateKotlinSources(
    taskName: String = "generateSources", sourceDirectoryName: String = "main", sourceFiles: List<SourceFile>
) {
    val generatedSourcesDir = buildDir.resolve("gen/$sourceDirectoryName/kotlin")

    val task = tasks.register(taskName) {
        generatedSourcesDir.mkdirs()

        for (sourceFile in sourceFiles) {
            val generatedVersionsSourceFile = generatedSourcesDir.resolve(sourceFile.filePath)
            generatedVersionsSourceFile.writeText(sourceFile.content)
        }
    }

    tasks.compileKotlin {
        dependsOn(task)
    }

    kotlin.sourceSets["main"].kotlin.srcDir(generatedSourcesDir)
}

fun Project.generateKotlinVersion(kotlinVersion: String) =
    generateKotlinSources(
        sourceFiles = listOf(
            SourceFile(
                "GeneratedKotlinVersion.kt",
                "internal const val kotlinVersion = \"$kotlinVersion\"\n"
            )
        )
    )


// copied and adapted from generated sources
// made private to avoid conflicts with generated code

private val TaskContainer.compileKotlin: TaskProvider<KotlinCompile>
    get() = named<KotlinCompile>("compileKotlin")

private val Project.kotlin: KotlinJvmProjectExtension
    get() = extensions.getByName("kotlin") as KotlinJvmProjectExtension
