package com.huanshankeji.benchmark

import com.huanshankeji.SourceSetType.Main
import com.huanshankeji.SourceSetType.RegisterSeparate
import com.huanshankeji.commonDependencies
import com.huanshankeji.sourceSets

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.benchmark")
    kotlin("plugin.allopen")
}

val extension = extensions.createKotlinxBenchmarkConventionsExtension()
extension.sourceSetType.convention(RegisterSeparate)

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

private fun configureConventions() {
    val sourceSetType = extension.sourceSetType.get()

    val MAIN = "main"
    val BENCHMAKRS = "benchmarks"

    if (sourceSetType == RegisterSeparate)
        sourceSets.create(BENCHMAKRS)

    dependencies {
        val implementationString: String
        when (sourceSetType) {
            Main -> implementationString = "implementation"
            RegisterSeparate -> {
                implementationString = "benchmarksImplementation"
                implementationString(with(sourceSets.main.get()) { output + runtimeClasspath })
            }
        }

        implementationString(commonDependencies.kotlinx.benchmark.runtime())
    }

    benchmark {
        targets {
            register(
                when (sourceSetType) {
                    Main -> MAIN
                    RegisterSeparate -> BENCHMAKRS
                }
            )
        }
    }
}

/*
`sourceSetType` is set in the build script after this plugin is applied, and it chooses which source set
to create. That cannot be wired as a lazy `Property`. kotlinx-benchmark realizes `benchmark.configurations`
inside its `afterEvaluate` before materializing `benchmark.targets`, which is late enough to see the
build script's value and early enough to register the target before materialization.
 */
private var conventionsConfigured = false
benchmark.configurations.configureEach {
    if (!conventionsConfigured) {
        conventionsConfigured = true
        configureConventions()
    }
}