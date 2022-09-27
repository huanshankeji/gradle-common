package com.huanshankeji

import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

// CPN: concatenated project name

fun getConcatenatedProjectNamePath(path: String) =
    path.splitToSequence(':').runningReduce { concatenatedName, name ->
        "$concatenatedName-$name"
    }.joinToString(":")


fun DependencyHandler.cpnProject(
    path: String,
    configuration: String? = null
): ProjectDependency =
    project(getConcatenatedProjectNamePath(path), configuration)
