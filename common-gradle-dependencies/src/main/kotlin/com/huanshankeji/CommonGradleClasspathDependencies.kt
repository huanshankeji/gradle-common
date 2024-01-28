package com.huanshankeji

import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec

class CommonGradleClasspathDependencies(val versions: CommonVersions) {
    inner class Kotlin internal constructor() {
        val group = "org.jetbrains.kotlin"

        inner class Plugin internal constructor() {
            inner class Serialization internal constructor() {
                val moduleName = "plugin.serialization"
                val defaultVersion get() = versions.kotlin

                fun PluginDependenciesSpec.applyPluginWithoutVersion() =
                    kotlin(moduleName)

                fun PluginDependenciesSpec.applyPluginWithVersion(version: String = defaultVersion) =
                    applyPluginWithoutVersion().version(version)
            }

            val serialization = Serialization()
        }

        val plugin = Plugin()

        @Deprecated("Use `plugin.serialization` instead.", ReplaceWith("plugin.serialization"))
        val serializationPlugin = plugin.serialization
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

    inner class Kotlinx internal constructor() {
        inner class Benchmark {
            val defaultVersion = versions.kotlinxBenchmark
            fun PluginDependenciesSpec.applyPluginWithVersion(version: String = defaultVersion) =
                id("org.jetbrains.kotlinx.benchmark").version(defaultVersion)

            fun pluginProject(version: String = defaultVersion) =
                "org.jetbrains.kotlinx:kotlinx-benchmark-plugin:$version"
        }

        val benchmark = Benchmark()
    }

    val kotlinx = Kotlinx()
}
