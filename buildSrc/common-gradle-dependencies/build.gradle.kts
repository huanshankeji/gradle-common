plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

// Source-link the `common-gradle-dependencies` sources so the build logic is compiled from
// the current source instead of a stale released version.
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied,
// because it reads the precompiled-script-plugin source directories eagerly at apply time
// (https://github.com/gradle/gradle/issues/21052).
sourceSets.main {
    kotlin.srcDir("../../common-gradle-dependencies/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")

// The root `:common-gradle-dependencies` project generates `GeneratedVersions` via
// `generateKotlinSources`, but this source-linking project does not evaluate that build
// script. Generate the same file here from the shared version catalog.
val generatedVersionsDir = layout.buildDirectory.dir("gen/main/kotlin")
val generateCatalogVersions = tasks.register("generateCatalogVersions") {
    val kotlinVersion = libs.versions.kotlin.get()
    val composeMultiplatformVersion = libs.versions.composeMultiplatform.get()
    val kotlinxBenchmarkVersion = libs.versions.kotlinx.benchmark.get()

    inputs.property("kotlin", kotlinVersion)
    inputs.property("composeMultiplatform", composeMultiplatformVersion)
    inputs.property("kotlinxBenchmark", kotlinxBenchmarkVersion)
    outputs.dir(generatedVersionsDir)

    doLast {
        val dir = generatedVersionsDir.get().asFile
        dir.mkdirs()
        dir.resolve("GeneratedVersions.kt").writeText(
            """
            |package com.huanshankeji
            |
            |internal object GeneratedVersions {
            |    internal const val kotlin = "$kotlinVersion"
            |    internal const val composeMultiplatform = "$composeMultiplatformVersion"
            |    internal const val kotlinxBenchmark = "$kotlinxBenchmarkVersion"
            |}
            |""".trimMargin()
        )
    }
}

tasks.named("compileKotlin") {
    dependsOn(generateCatalogVersions)
}

kotlin.sourceSets.getByName("main").kotlin.srcDir(generatedVersionsDir)
