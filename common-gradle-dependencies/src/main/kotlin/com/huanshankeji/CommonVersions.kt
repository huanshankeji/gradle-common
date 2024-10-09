package com.huanshankeji

class CommonVersions @JvmOverloads constructor(
    val kotlin: String = GeneratedVersions.kotlin,

    val kotlinCommon: String = "0.4.0",

    val kotlinxCoroutines: String = "1.8.1",
    val kotlinxHtml: String = "0.11.0",
    val kotlinxSerialization: String = "1.7.1", // "1.7.2" uses Kotlin 2.0.20 and seems to cause problems.
    val kotlinxDatetime: String = "0.6.0",
    val kotlinxBenchmark: String = GeneratedVersions.kotlinxBenchmark,
    val exposed: String = "0.53.0", // "0.54.0" deprecates the old DSL APIs as errors while we still depend on them.
    val ktor: String = "2.3.11",
    val composeMultiplatform: String = GeneratedVersions.composeMultiplatform, // this is usually only used in classpath dependencies
    val androidx: Androidx = Androidx(),

    val vertx: String = "4.5.10",
    val arrow: String = "1.2.4",
    val orgJunit: String = "5.10.2", // JUnit 5 actually
    val kotest: String = "5.9.0",
    val postgreSql: String = "42.7.3",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.19.8"
) {
    class Androidx(
        val activityCompose: String = "1.9.2"
    )
}