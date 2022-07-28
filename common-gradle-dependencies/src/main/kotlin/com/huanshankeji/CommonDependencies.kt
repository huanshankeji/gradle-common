package com.huanshankeji

import org.gradle.api.artifacts.dsl.DependencyHandler

// some but not all default dependencies
class CommonDependencies(val versions: CommonVersions = CommonVersions()) {
    inner class KotlinCommon internal constructor() {
        val defaultVersion = versions.kotlinCommon
        fun module(module: String, version: String = defaultVersion) =
            "com.huanshankeji:kotlin-common-$module:$version"

        fun core(version: String = defaultVersion) = module("core", version)
        fun net(version: String = defaultVersion) = module("net", version)
        fun web(version: String = defaultVersion) = module("web", version)
        fun arrow(version: String = defaultVersion) = module("arrow", version)
        fun coroutines(version: String = defaultVersion) = module("coroutines", version)
        fun exposed(version: String = defaultVersion) = module("exposed", version)

        inner class Ktor internal constructor() {
            fun module(module: String, version: String = defaultVersion) =
                this@KotlinCommon.module("ktor-$module", version)

            fun client(version: String = defaultVersion) = module("client", version)
        }

        val ktor = Ktor()

        fun serialization(version: String = defaultVersion) = module("serialization", version)
        fun vertx(version: String = defaultVersion) = module("vertx", version)

        inner class Vertx internal constructor() {
            fun module(module: String, version: String = defaultVersion) =
                this@KotlinCommon.module("vertx-$module", version)

            fun withContextReceivers(version: String = defaultVersion) =
                module("with-context-receivers", version)
        }

        val vertx = Vertx()
    }

    val kotlinCommon = KotlinCommon()

    inner class Kotlinx internal constructor() {
        inner class Coroutines internal constructor() {
            val defaultVersion = versions.kotlinxCoroutines
            fun module(module: String, version: String = defaultVersion) =
                kotlinx("coroutines-$module", version)

            fun core(version: String = defaultVersion) = module("core", version)
            fun test(version: String = defaultVersion) = module("test", version)
        }

        val coroutines = Coroutines()

        inner class Html internal constructor() {
            val defaultVersion = versions.kotlinxHtml
            fun module(module: String, version: String = defaultVersion) =
                "org.jetbrains.kotlinx:kotlinx-html-$module:$version"
        }

        val html = Html()

        inner class Serialization internal constructor() {
            val defaultVersion = versions.kotlinxSerialization
            fun module(module: String, version: String = defaultVersion) =
                kotlinx("serialization-$module", version)

            fun core(version: String = defaultVersion) = module("core", version)
            fun protobuf(version: String = defaultVersion) = module("protobuf", version)
            fun json(version: String = defaultVersion) = module("json", version)
        }

        val serialization = Serialization()

        fun datetime(version: String = versions.kotlinxDatetime) =
            kotlinx("datetime", version)
    }

    val kotlinx = Kotlinx()


    // official libraries from JetBrains

    inner class Exposed internal constructor() {
        val defaultVersion = versions.exposed
        fun module(module: String, version: String = defaultVersion) =
            "org.jetbrains.exposed:exposed-$module:$version"

        fun core(version: String = defaultVersion) =
            module("core", version)
    }

    val exposed = Exposed()

    inner class Ktor internal constructor() {
        val defaultVersion = versions.ktor
        fun module(module: String, version: String = defaultVersion) =
            "io.ktor:ktor-$module:$version"

        inner class Client internal constructor() {
            fun module(module: String, version: String = defaultVersion) =
                this@Ktor.module("client-$module", version)

            fun core(version: String = defaultVersion) =
                module("core", version)
        }

        val client = Client()
    }

    val ktor = Ktor()


    // others

    inner class Vertx internal constructor() {
        val defaultVersion = versions.vertx

        private fun stackDepchain(version: String = defaultVersion) =
            moduleWithVersion("stack-depchain", version)

        fun DependencyHandler.platformStackDepchain(version: String = defaultVersion) =
            platform(stackDepchain(version))

        fun moduleWithoutVersion(module: String) =
            "io.vertx:vertx-$module"

        fun moduleWithVersion(module: String, version: String = defaultVersion) =
            "${moduleWithoutVersion(module)}:$version"
    }

    val vertx = Vertx()

    inner class Arrow internal constructor() {
        val defaultVersion = versions.arrow
        fun module(module: String, version: String = defaultVersion) =
            "io.arrow-kt:arrow-$module:$version"

        fun core(version: String = defaultVersion) =
            module("core", version)
    }

    val arrow = Arrow()

    inner class OrgJunit internal constructor() {
        val defaultVersion = versions.orgJunit

        private fun bom(version: String = defaultVersion) =
            "org.junit:junit-bom:$version"

        fun DependencyHandler.platformBom(version: String = defaultVersion) =
            platform(bom(version))

        inner class Jupiter internal constructor() {
            fun withVersion(version: String = defaultVersion) =
                "${withoutVersion()}:$version"

            fun withoutVersion() =
                "org.junit.jupiter:junit-jupiter"

            fun moduleWithVersion(module: String, version: String = defaultVersion) =
                "${moduleWithoutVersion(module)}:$version"

            fun moduleWithoutVersion(module: String) =
                "org.junit.jupiter:junit-jupiter-$module"

            fun apiWithVersion(version: String = defaultVersion) =
                moduleWithVersion("api", version)

            fun apiWithoutVersion() =
                moduleWithoutVersion("api")

            fun engineWithVersion(version: String = defaultVersion) =
                moduleWithVersion("engine", version)

            fun engineWithoutVersion() =
                moduleWithoutVersion("engine")
        }

        val jupiter = Jupiter()
    }

    val orgJunit = OrgJunit()

    inner class Kotest internal constructor() {
        val defaultVersion = versions.kotest
        fun module(module: String, version: String = defaultVersion) =
            "io.kotest:kotest-$module:$version"

        fun property(version: String = defaultVersion) =
            module("property", version)
    }

    val kotest = Kotest()

    fun postgreSql(version: String = versions.postgreSql) =
        "org.postgresql:postgresql:$version"

    inner class Slf4j internal constructor() {
        val defaultVersion = versions.slf4j
        fun module(module: String, version: String = defaultVersion) =
            "org.slf4j:slf4j-$module:$version"

        fun api(version: String = defaultVersion) =
            module("api", version)

        fun simple(version: String = defaultVersion) =
            module("simple", version)
    }

    val slf4j = Slf4j()
}