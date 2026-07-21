package com.huanshankeji

import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings

// CPN: concatenated project name
// File name differs from project-gradle-plugins' ConcatenatedProjectNames.kt so the JVM
// file facades don't clash when both jars appear on related classloaders.

/**
 * Renames this project descriptor and its descendants so each name is prefixed by its parent's
 * concatenated name. This is mainly for Maven publications: with default Gradle publishing,
 * [org.gradle.api.Project.name] becomes the artifactId, and concatenating avoids
 * collisions when subprojects under different parents share a simple name (e.g. `:a:b` and `:x:b`
 * are both named `b`). Manually setting `artifactId` on the Maven publication for Kotlin
 * Multiplatform used to cause bugs in practice, so this project-name approach is preferred instead.
 *
 * Call on [Settings.rootProject] after all [Settings.include] calls — for example
 * [Settings.setProjectConcatenatedNames]. A settings [org.gradle.api.Plugin] applied in `plugins {}`
 * runs before subsequent `include` calls in the settings script, so it cannot perform this
 * rename synchronously in [org.gradle.api.Plugin.apply]; call this explicitly at the end of
 * the settings script instead.
 *
 * After renaming, use `getConcatenatedProjectNamePath` / `cpnProject` in build scripts to refer
 * to projects by their logical paths.
 */
fun ProjectDescriptor.setProjectConcatenatedNames(prefix: String) {
    name = prefix + name
    for (child in children)
        child.setProjectConcatenatedNames("$name-")
}

fun Settings.setProjectConcatenatedNames() =
    rootProject.setProjectConcatenatedNames("")
