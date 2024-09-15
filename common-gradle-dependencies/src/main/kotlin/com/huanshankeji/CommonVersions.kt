package com.huanshankeji

class CommonVersions @JvmOverloads constructor(
    val kotlin: String = GeneratedVersions.kotlin,

    val kotlinCommon: String = "0.4.0",

    val kotlinxCoroutines: String = "1.8.1",
    val kotlinxHtml: String = "0.11.0",
    val kotlinxSerialization: String = "1.7.2",
    val kotlinxDatetime: String = "0.6.0",
    val kotlinxBenchmark: String = GeneratedVersions.kotlinxBenchmark,
    val exposed: String = "0.50.1",
    val ktor: String = "2.3.11",
    val composeMultiplatform: String = GeneratedVersions.composeMultiplatform, // this is usually only used in classpath dependencies

    val vertx: String = "4.5.7",
    val arrow: String = "1.2.4",
    val orgJunit: String = "5.10.2", // JUnit 5 actually
    val kotest: String = "5.9.0",
    val postgreSql: String = "42.7.3",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.19.8"
)