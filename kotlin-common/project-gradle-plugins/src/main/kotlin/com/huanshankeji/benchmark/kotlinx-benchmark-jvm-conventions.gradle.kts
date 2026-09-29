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
extension.applySourceSetType = { sourceSetType ->
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

// Fail-fast validation only. See https://docs.gradle.org/current/userguide/best_practices_general.html#when_afterevaluate_may_still_be_appropriate.
afterEvaluate {
    extension.ensureSourceSetTypeCalled()
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}
