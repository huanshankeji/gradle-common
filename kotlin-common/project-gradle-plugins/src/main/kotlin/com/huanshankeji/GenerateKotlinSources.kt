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
    val generatedSourcesDir = layout.buildDirectory.dir("gen/$sourceDirectoryName/kotlin").get().asFile

    val task = tasks.register(taskName) {
        // Declare inputs based on source file content to trigger re-generation when content changes
        inputs.property("sourceFiles") { sourceFiles.associate { it.filePath to it.content } }

        // Declare outputs so Gradle can check if files exist and are up-to-date
        outputs.dir(generatedSourcesDir)

        doLast {
            generatedSourcesDir.mkdirs()

            for (sourceFile in sourceFiles) {
                val generatedVersionsSourceFile = generatedSourcesDir.resolve(sourceFile.filePath)
                generatedVersionsSourceFile.writeText(sourceFile.content)
            }
        }
    }

    tasks.compileKotlin {
        dependsOn(task)
    }

    // Ensure tasks that access source sets have explicit dependency on the generation task
    //tasks.named("sourcesJar") { // This seems to configure eagerly and doesn't work.
    tasks.named { it == "sourcesJar" }.configureEach {
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

// TODO: should be adapted for Kotlin Multiplatform
fun Project.generateRootProjectName() =
    generateKotlinSources(
        sourceFiles = listOf(
            SourceFile(
                "GeneratedRootProjectName.kt",
                "internal const val ROOT_PROJECT_NAME = \"${rootProject.name}\"\n"
            )
        )
    )


// copied and adapted from generated sources
// made private to avoid conflicts with generated code

private val TaskContainer.compileKotlin: TaskProvider<KotlinCompile>
    get() = named<KotlinCompile>("compileKotlin")

private val Project.kotlin: KotlinJvmProjectExtension
    get() = extensions.getByName("kotlin") as KotlinJvmProjectExtension
