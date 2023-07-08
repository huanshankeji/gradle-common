package com.huanshankeji

import kotlinVersion

class CommonVersions @JvmOverloads constructor(
    val kotlin: String = kotlinVersion,

    val kotlinCommon: String = "0.3.0",

    val kotlinxCoroutines: String = "1.7.1",
    val kotlinxHtml: String = "0.8.0",
    val kotlinxSerialization: String = "1.5.1",
    val kotlinxDatetime: String = "0.4.0",
    val kotlinxBenchmark: String = "0.4.8",
    val exposed: String = "0.41.1",
    val ktor: String = "2.3.1",
    val composeMultiplatform: String = "1.4.0",

    val vertx: String = "4.4.3",
    val arrow: String = "1.2.0-RC",
    val orgJunit: String = "5.9.3",
    val kotest: String = "5.6.2",
    val postgreSql: String = "42.5.4",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.18.3"
)