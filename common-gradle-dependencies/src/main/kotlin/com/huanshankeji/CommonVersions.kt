package com.huanshankeji

import GeneratedVersions

class CommonVersions(
    val kotlin: String = GeneratedVersions.kotlin,

    // https://github.com/huanshankeji/kotlin-common/releases
    val kotlinCommon: String = "0.6.1",

    // https://github.com/Kotlin/kotlinx.coroutines/releases
    val kotlinxCoroutines: String = "1.10.2",
    // https://github.com/Kotlin/kotlinx.html/releases
    val kotlinxHtml: String = "0.12.0",
    // https://github.com/Kotlin/kotlinx.serialization/releases
    val kotlinxSerialization: String = "1.9.0",
    // https://github.com/Kotlin/kotlinx-datetime/releases
    val kotlinxDatetime: String = "0.7.1",
    val kotlinxBenchmark: String = GeneratedVersions.kotlinxBenchmark,
    // https://github.com/Kotlin/kotlinx-io/releases
    val kotlinxIo: String = "0.8.0",
    // https://github.com/JetBrains/Exposed/releases
    val exposed: String = "1.0.0-rc-3",
    // https://github.com/ktorio/ktor/releases
    val ktor: String = "3.3.1",
    val jetBrainsAndroidx: JetbrainsAndroidx = JetbrainsAndroidx(),
    val androidx: Androidx = Androidx(),

    // https://vertx.io/blog/category/releases/, https://github.com/eclipse-vertx/vert.x/tags
    val vertx: String = "5.0.5",
    // https://github.com/arrow-kt/arrow/releases
    val arrow: String = "2.1.2",
    // https://docs.junit.org/6.0.0/release-notes/, https://github.com/junit-team/junit-framework/releases
    val orgJunit: String = "5.14.0", // JUnit 5 actually // JUnit 6 requires Java 17
    // https://github.com/kotest/kotest/releases
    val kotest: String = "6.0.4",
    // https://jdbc.postgresql.org/changelogs/, https://github.com/pgjdbc/pgjdbc/releases
    val postgreSql: String = "42.7.8",
    // https://github.com/qos-ch/slf4j/tags
    val slf4j: String = "2.0.17",
    // TODO add kotlin-logging (https://github.com/oshai/kotlin-logging)
    // https://github.com/testcontainers/testcontainers-java/releases
    val testcontainers: String = "2.0.3"
) {
    class JetbrainsAndroidx(
        val composeMultiplatform: String = GeneratedVersions.composeMultiplatform, // this is usually only used in classpath dependencies
        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-lifecycle.html
        val lifecycle: String = "2.9.5",
        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html
        val navigation: String = "2.9.1",
    )

    val composeMultiplatform get() = jetBrainsAndroidx.composeMultiplatform

    // https://developer.android.com/jetpack/androidx/versions
    class Androidx(
        // https://developer.android.com/jetpack/androidx/releases/activity
        val activity: String = "1.11.0",
        val compose: Compose = Compose()
    ) {
        class Compose(
            // https://developer.android.com/jetpack/androidx/releases/compose-compiler
            val compiler: String = "1.5.15",
            // https://developer.android.com/jetpack/androidx/releases/compose-foundation
            val common: String = "1.9.4", // for "animation", "foundation", "material", "runtime", and "ui"
            // https://developer.android.com/jetpack/androidx/releases/compose-material3
            val material3: String = "1.4.0",
            // https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive
            val material3Adaptive: String = "1.2.0"
        )
    }
}