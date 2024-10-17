package com.huanshankeji

class CommonVersions @JvmOverloads constructor(
    val kotlin: String = GeneratedVersions.kotlin,

    val kotlinCommon: String = "0.4.0",

    val kotlinxCoroutines: String = "1.9.0",
    val kotlinxHtml: String = "0.11.0",
    val kotlinxSerialization: String = "1.7.1", // "1.7.2" and "1.7.3" use Kotlin 2.0.20 and seem to cause problems with Kotlin 2.0.10 builds.
    val kotlinxDatetime: String = "0.6.1",
    val kotlinxBenchmark: String = GeneratedVersions.kotlinxBenchmark,
    val exposed: String = "0.53.0", // "0.54.0" deprecates the old DSL APIs as errors while we still depend on them.
    val ktor: String = "2.3.12", // "3.0.0" uses Kotlin 2.0.20 too.
    val composeMultiplatform: String = GeneratedVersions.composeMultiplatform, // this is usually only used in classpath dependencies
    val androidx: Androidx = Androidx(),

    val vertx: String = "4.5.10",
    val arrow: String = "1.2.4",
    val orgJunit: String = "5.11.2", // JUnit 5 actually
    val kotest: String = "5.9.1", // v6.0.0.M1 is available already
    val postgreSql: String = "42.7.4",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.20.2"
) {
    class Androidx(
        val activityCompose: String = "1.9.2"
    )
}