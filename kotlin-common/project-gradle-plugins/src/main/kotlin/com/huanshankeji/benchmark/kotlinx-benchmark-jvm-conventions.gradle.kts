package com.huanshankeji.benchmark

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.benchmark")
    kotlin("plugin.allopen")
}

configureKotlinxBenchmarkJvm()