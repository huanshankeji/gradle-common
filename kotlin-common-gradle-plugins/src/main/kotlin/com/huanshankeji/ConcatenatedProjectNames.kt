package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

// CPN: concatenated project name

/**
 * Renames this project descriptor and its descendants so each name is prefixed by its parent's
 * concatenated name. Call on [Settings.rootProject] after all [Settings.include] calls — for
 * example `rootProject.setProjectConcatenatedNames("")`. A [org.gradle.api.Plugin] applied in
 * `plugins {}` runs before `include` and cannot perform this rename.
 *
 * After renaming, use [getConcatenatedProjectNamePath] / [cpnProject] in build scripts to refer
 * to projects by their logical paths.
 */
fun ProjectDescriptor.setProjectConcatenatedNames(prefix: String) {
    name = prefix + name
    for (child in children)
        child.setProjectConcatenatedNames("$name-")
}

fun Settings.setProjectConcatenatedNames() {
    rootProject.setProjectConcatenatedNames("")
}

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
    project: Project, path: String, configuration: String? = null
): ProjectDependency =
    project(project.getConcatenatedProjectNamePath(path), configuration)

fun KotlinDependencyHandler.cpnProject(
    project: Project, path: String, configuration: String? = null
): ProjectDependency =
    project(project.getConcatenatedProjectNamePath(path), configuration)
