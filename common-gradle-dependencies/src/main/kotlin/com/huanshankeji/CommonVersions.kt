package com.huanshankeji

import GeneratedVersions

class CommonVersions(
    val kotlin: String = GeneratedVersions.kotlin,

    val kotlinCommon: String = "0.5.1",

    val kotlinxCoroutines: String = "1.9.0",
    val kotlinxHtml: String = "0.11.0",
    val kotlinxSerialization: String = "1.7.3",
    val kotlinxDatetime: String = "0.6.1",
    val kotlinxBenchmark: String = GeneratedVersions.kotlinxBenchmark,
    val kotlinxIo: String = "0.6.0",
    val exposed: String = "0.56.0",
    val ktor: String = "3.0.1",
    val androidx: Androidx = Androidx(),

    val vertx: String = "4.5.11", // "5.0.0.CR2" is available but it's used here yet. See https://vertx.io/docs/guides/vertx-5-migration-guide/.
    val arrow: String = "2.0.0-rc.1",
    val orgJunit: String = "5.11.3", // JUnit 5 actually
    val kotest: String = "6.0.0.M1", // v6.0.0.M1 is available already
    val postgreSql: String = "42.7.4",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.20.4"
) {
    class JetbrainsAndroidx(
        val composeMultiplatform: String = GeneratedVersions.composeMultiplatform, // this is usually only used in classpath dependencies
        val lifecycle: String = "2.8.4",
        val navigation: String = "2.8.0-alpha10",
    )

    val jetBrainsAndroidx = JetbrainsAndroidx()

    val composeMultiplatform get() = jetBrainsAndroidx.composeMultiplatform

    // https://developer.android.com/jetpack/androidx/versions
    class Androidx(
        val activity: String = "1.9.3",
        val compose: Compose = Compose()
    ) {
        class Compose(
            val compiler: String = "1.5.15",
            val common: String = "1.7.5", // for "animation", "foundation", "material","runtime", and "ui"
            val material3: String = "1.3.1",
            val material3Adaptive: String = "1.0.0" // This version is not available yet. I don't remember why I added this. Maybe it's for the future.
        )
    }
}