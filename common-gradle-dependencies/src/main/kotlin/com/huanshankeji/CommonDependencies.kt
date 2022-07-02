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
}