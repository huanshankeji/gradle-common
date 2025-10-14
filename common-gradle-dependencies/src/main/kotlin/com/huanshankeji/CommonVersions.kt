package com.huanshankeji

import GeneratedVersions

class CommonVersions(
    val kotlin: String = GeneratedVersions.kotlin,

    val kotlinCommon: String = "0.6.1",

    val kotlinxCoroutines: String = "1.10.2",
    val kotlinxHtml: String = "0.12.0",
    val kotlinxSerialization: String = "1.9.0",
    val kotlinxDatetime: String = "0.7.1",
    val kotlinxBenchmark: String = GeneratedVersions.kotlinxBenchmark,
    val kotlinxIo: String = "0.8.0",
    val exposed: String = "1.0.0-rc-1",
    val ktor: String = "3.3.0",
    val jetBrainsAndroidx: JetbrainsAndroidx = JetbrainsAndroidx(),
    val androidx: Androidx = Androidx(),

    val vertx: String = "5.0.4", // "5.0.0.CR2" is available but it's used here yet. See https://vertx.io/docs/guides/vertx-5-migration-guide/. // TODO remove this comment if this version works
    val arrow: String = "2.1.2",
    val orgJunit: String = "6.0.0", // JUnit 5/6 actually // TODO Revert if it's hard to migrate. Otherwise, remove this TODO.
    val kotest: String = "6.0.3", // v6.0.0.M1 is available already // TODO remove this comment if this version works
    val postgreSql: String = "42.7.7",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.21.3"
) {
    class JetbrainsAndroidx(
        val composeMultiplatform: String = GeneratedVersions.composeMultiplatform, // this is usually only used in classpath dependencies
        val lifecycle: String = "2.9.1",
        val navigation: String = "2.9.0",
    )

    val composeMultiplatform get() = jetBrainsAndroidx.composeMultiplatform

    // https://developer.android.com/jetpack/androidx/versions
    class Androidx(
        val activity: String = "1.11.0",
        val compose: Compose = Compose()
    ) {
        class Compose(
            val compiler: String = "1.5.15",
            val common: String = "1.9.1", // for "animation", "foundation", "material", "runtime", and "ui"
            val material3: String = "1.5.0-alpha04", // or "1.4.0-rc01"
            val material3Adaptive: String = "1.2.0-beta02"
        )
    }
}