package com.huanshankeji

import org.gradle.plugin.use.PluginDependenciesSpec

class CommonGradleClasspathDependencies(val versions: CommonVersions) {
    inner class Kotlin internal constructor() {
        val group = "org.jetbrains.kotlin"

        inner class SerializationPlugin internal constructor() {
            val moduleName = "plugin.serialization"
            val version get() = versions.kotlin
        }

        val serializationPlugin = SerializationPlugin()
    }

    val kotlin = Kotlin()

    inner class ComposeJb internal constructor() {
        val gradlePluginProjectGroupAndArtifact = "org.jetbrains.compose:compose-gradle-plugin"
        val gradlePluginId = "org.jetbrains.compose"
        val defaultVersion = versions.composeJb


        fun PluginDependenciesSpec.applyPluginWithoutVersion() =
            id(gradlePluginId)

        fun PluginDependenciesSpec.applyPluginWithVersion(version: String = defaultVersion) =
            applyPluginWithoutVersion().version(version)
    }

    val composeJb = ComposeJb()
}
