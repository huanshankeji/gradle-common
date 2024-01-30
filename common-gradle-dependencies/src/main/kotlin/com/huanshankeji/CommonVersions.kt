package com.huanshankeji

import kotlinVersion

class CommonVersions @JvmOverloads constructor(
    val kotlin: String = kotlinVersion,

    val kotlinCommon: String = "0.3.0",

    val kotlinxCoroutines: String = "1.7.3",
    val kotlinxHtml: String = "0.9.1",
    val kotlinxSerialization: String = "1.6.0",
    val kotlinxDatetime: String = "0.4.1",
    val kotlinxBenchmark: String = "0.4.9",
    val exposed: String = "0.44.1",
    val ktor: String = "2.3.6",
    val composeMultiplatform: String = "1.5.10", // this is usually only used in classpath dependencies

    val vertx: String = "4.4.6", // TODO bump to "4.5.0". There are some breaking changes however. See https://github.com/vert-x3/wiki/wiki/4.5.0-Deprecations-and-breaking-changes.
    val arrow: String = "1.2.1",
    val orgJunit: String = "5.10.1",
    val kotest: String = "5.8.0",
    val postgreSql: String = "42.6.0",
    val slf4j: String = "1.7.36", // TODO: consider replacing with kotlin-logging (https://github.com/oshai/kotlin-logging)
    val testContainers: String = "1.19.1"
)