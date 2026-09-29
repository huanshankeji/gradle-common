package com.huanshankeji.benchmark

import com.huanshankeji.commonDependencies
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlinx.benchmark")
    kotlin("plugin.allopen")
}

/*
There are 2 reasons creating a benchmark(s) module is not supported:
1. I didn't find an official way to add a `commonBenchmarks` compilation and make it depend on `commonMain;
1. The kotlinx-benchmark plugin materializes benchmark tasks in `afterEvaluate`, so benchmark(s) module dependencies can't be added.
 */

/*
val extension = extensions.createKotlinxBenchmarkConventionsExtension()
val sourceSetType = extension.sourceSetType.getOrElse(RegisterSeparate)
*/

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(commonDependencies.kotlinx.benchmark.runtime())
    }
}

benchmark {
    val benchmarkTargets = targets
    kotlin.targets.configureEach {
        if (platformType != KotlinPlatformType.common)
            benchmarkTargets.register(name)
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}
