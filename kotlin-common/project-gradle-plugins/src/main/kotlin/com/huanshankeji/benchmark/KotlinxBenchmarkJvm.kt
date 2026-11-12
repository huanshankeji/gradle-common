package com.huanshankeji.benchmark

import com.huanshankeji.SourceSetType
import com.huanshankeji.SourceSetType.Main
import com.huanshankeji.SourceSetType.RegisterSeparate
import com.huanshankeji.commonDependencies
import com.huanshankeji.sourceSets
import kotlinx.benchmark.gradle.BenchmarksExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

/**
 * Configures JVM benchmarks immediately, so the selected source set is available to the build script.
 * Call once instead of applying the JVM benchmark convention plugin when selecting [Main].
 * Apply `org.jetbrains.kotlinx.benchmark` in `plugins {}` if the build script also needs its Kotlin DSL accessors.
 */
fun Project.configureKotlinxBenchmarkJvm(sourceSetType: SourceSetType = RegisterSeparate) {
    pluginManager.apply("org.jetbrains.kotlin.jvm")
    pluginManager.apply("org.jetbrains.kotlinx.benchmark")
    pluginManager.apply("org.jetbrains.kotlin.plugin.allopen")

    val sourceSet = when (sourceSetType) {
        Main -> sourceSets.getByName("main")
        RegisterSeparate -> sourceSets.create("benchmarks").also {
            dependencies.add(
                it.implementationConfigurationName,
                with(sourceSets.getByName("main")) { output + runtimeClasspath }
            )
        }
    }

    dependencies.add(sourceSet.implementationConfigurationName, commonDependencies.kotlinx.benchmark.runtime())

    extensions.configure<BenchmarksExtension> {
        targets.register(sourceSet.name)
    }

    extensions.configure<AllOpenExtension> {
        annotation("org.openjdk.jmh.annotations.State")
    }
}
