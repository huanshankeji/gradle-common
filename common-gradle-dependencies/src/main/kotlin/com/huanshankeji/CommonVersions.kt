package com.huanshankeji

import kotlinVersion

class CommonVersions @JvmOverloads constructor(
    val kotlin: String = kotlinVersion,

    val kotlinCommon: String = "0.3.0",

    val kotlinxCoroutines: String = "1.8.0",
    val kotlinxHtml: String = "0.11.0",
    val kotlinxSerialization: String = "1.6.3",
    val kotlinxDatetime: String = "0.5.0",
    val kotlinxBenchmark: String = "0.4.10",
    val exposed: String = "0.48.0",
    val ktor: String = "2.3.9",
    val composeMultiplatform: String = "1.6.1", // this is usually only used in classpath dependencies

    val vertx: String = "4.5.5",
    val arrow: String = "1.2.3",
    val orgJunit: String = "5.10.2",
    val kotest: String = "5.8.1",
    val postgreSql: String = "42.7.2",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.19.7"
)