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
1. The upstream benchmark plugin configures compilations after project evaluation so benchmark(s) module dependencies can't be added.
 */

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(commonDependencies.kotlinx.benchmark.runtime())
    }
}

kotlin.targets.all {
    benchmark.targets.register(name)
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}
