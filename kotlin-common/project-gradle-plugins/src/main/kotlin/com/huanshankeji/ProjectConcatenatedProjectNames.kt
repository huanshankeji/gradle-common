package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

// CPN: concatenated project name — for project deps in build scripts
// (pure path helper in kotlin-common-gradle-library; settings rename in settings-gradle-plugins)

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
