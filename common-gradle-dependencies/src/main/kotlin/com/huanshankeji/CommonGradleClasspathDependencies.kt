package com.huanshankeji

import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

class CommonGradleClasspathDependencies(val versions: CommonVersions) {
    inner class Kotlin internal constructor() {
        val group = "org.jetbrains.kotlin"

        inner class SerializationPlugin internal constructor() {
            val moduleName = "plugin.serialization"
            val version get() = versions.kotlin

            fun PluginDependenciesSpec.apply() =
                kotlin(moduleName) version version
        }

        val serializationPlugin = SerializationPlugin()
    }

    val kotlin = Kotlin()

    inner class ComposeMultiplatform internal constructor() {
        val defaultVersion = versions.composeMultiplatform

        inner class GradlePlugin {
            fun PluginDependenciesSpec.applyPluginWithoutVersion() =
                id("org.jetbrains.compose")

            fun PluginDependenciesSpec.applyPluginWithVersion(version: String = defaultVersion) =
                applyPluginWithoutVersion().version(version)

            fun pluginProject(version: String = defaultVersion) =
                "org.jetbrains.compose:compose-gradle-plugin:$version"
        }

        val gradlePlugin = GradlePlugin()
    }

    val composeMultiplatform = ComposeMultiplatform()
}
