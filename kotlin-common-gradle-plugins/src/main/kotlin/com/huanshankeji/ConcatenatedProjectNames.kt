package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

// CPN: concatenated project name

fun getConcatenatedProjectNamePath(rootProjectName: String, path: String): String {
    val names = path.splitToSequence(':')
    require(names.first() == "")
    return names.drop(1).scan(rootProjectName) { concatenatedName, name ->
        "$concatenatedName-$name"
    }.drop(1).joinToString(":", ":")
}

fun Project.getConcatenatedProjectNamePath(path: String) =
    getConcatenatedProjectNamePath(rootProject.name, path)

// TODO: use context receivers when it's stable
fun DependencyHandler.cpnProject(
    project: Project,
    path: String,
    configuration: String? = null
): ProjectDependency =
    project(project.getConcatenatedProjectNamePath(path), configuration)
