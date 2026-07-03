package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

// CPN: concatenated project name

// for a settings script

/**
 * Renames this project descriptor and its descendants so each name is prefixed by its parent's
 * concatenated name. This is mainly for Maven publications: with default Gradle publishing,
 * [Project.name] becomes the artifactId, and concatenating avoids collisions when subprojects
 * under different parents share a simple name (e.g. `:a:b` and `:x:b` are both named `b`).
 * Manually setting `artifactId` on the Maven publication for Kotlin Multiplatform used to cause
 * bugs in practice, so this project-name approach is preferred instead.
 *
 * Call on [Settings.rootProject] after all [Settings.include] calls — for example
 * `setProjectConcatenatedNames()`. A settings [org.gradle.api.Plugin] applied in `plugins {}`
 * runs before subsequent `include` calls in the settings script, so it cannot perform this
 * rename synchronously in [org.gradle.api.Plugin.apply]; call this explicitly at the end of
 * the settings script instead.
 *
 * After renaming, use [getConcatenatedProjectNamePath] / [cpnProject] in build scripts to refer
 * to projects by their logical paths.
 */
fun ProjectDescriptor.setProjectConcatenatedNames(prefix: String) {
    name = prefix + name
    for (child in children)
        child.setProjectConcatenatedNames("$name-")
}

fun Settings.setProjectConcatenatedNames() =
    rootProject.setProjectConcatenatedNames("")

// for consuming projects in build scripts

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
