package com.huanshankeji.benchmark

import com.huanshankeji.commonDependencies
import org.gradle.kotlin.dsl.invoke

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlinx.benchmark")
    kotlin("plugin.allopen")
}

/*
There are 2 reasons creating a benchmark(s) module is not supported:
1. I didn't find an official way to add a `commonBenchmarks` compilation and make it depend on `commonMain;
1. The benchmark targets are added in `afterEvaluate` so benchmark(s) module dependencies can't be added.
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

afterEvaluate {
    benchmark {
        targets {
            kotlin.targets.forEach { register(it.name) }
        }
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}
