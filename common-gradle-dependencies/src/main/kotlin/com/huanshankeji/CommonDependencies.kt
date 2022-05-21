package com.huanshankeji

// some but not all default dependencies
object CommonDependencies {
    object KotlinCommon {
        val defaultVersion = DefaultVersions.kotlinCommon
        fun module(module: String, version: String = defaultVersion) =
            "com.huanshankeji:kotlin-common-$module:$version"

        fun core(version: String = defaultVersion) = module("core", version)
        fun net(version: String = defaultVersion) = module("net", version)
        fun web(version: String = defaultVersion) = module("web", version)
        fun arrow(version: String = defaultVersion) = module("arrow", version)
        fun coroutines(version: String = defaultVersion) = module("coroutines", version)
        fun exposed(version: String = defaultVersion) = module("exposed", version)

        object Ktor {
            fun module(module: String, version: String = defaultVersion) =
                KotlinCommon.module("ktor-$module", version)

            fun client(version: String = defaultVersion) = module("client", version)
        }

        fun serialization(version: String = defaultVersion) = module("serialization", version)
        fun vertx(version: String = defaultVersion) = module("vertx", version)
    }

    object Kotlinx {
        object Coroutines {
            val defaultVersion = DefaultVersions.kotlinxCoroutines
            fun module(module: String, version: String = defaultVersion) =
                kotlinx("coroutines-$module", version)

            fun core(version: String = defaultVersion) = module("core", version)
            fun test(version: String = defaultVersion) = module("test", version)
        }

        object Html {
            val defaultVersion = DefaultVersions.kotlinxHtml
            fun module(module: String, version: String = defaultVersion) =
                "org.jetbrains.kotlinx:kotlinx-html-$module:$version"
        }

        object Serialization {
            val defaultVersion = DefaultVersions.kotlinxSerialization
            fun module(module: String, version: String = defaultVersion) =
                kotlinx("serialization-$module", version)

            fun core(version: String = defaultVersion) = module("core", version)
            fun protobuf(version: String = defaultVersion) = module("protobuf", version)
            fun json(version: String = defaultVersion) = module("json", version)
        }
    }


    // official libraries from JetBrains

    object Exposed {
        val defaultVersion = DefaultVersions.exposed
        fun module(module: String, version: String = defaultVersion) =
            "org.jetbrains.exposed:exposed-$module:$version"

        fun core(version: String = defaultVersion) =
            module("core", version)
    }

    object Ktor {
        val defaultVersion = DefaultVersions.ktor
        fun module(module: String, version: String = defaultVersion) =
            "io.ktor:ktor-$module:$version"

        object Client {
            fun module(module: String, version: String = defaultVersion) =
                Ktor.module("client-$module", version)

            fun core(version: String = defaultVersion) =
                module("core", version)
        }
    }


    // others

    object Vertx {
        val defaultVersion = DefaultVersions.vertx
        fun stackDepchain(version: String = defaultVersion) =
            "io.vertx:vertx-stack-depchain:$version"

        fun module(module: String) =
            "io.vertx:vertx-$module"
    }

    object Arrow {
        val defaultVersion = DefaultVersions.arrow
        fun module(module: String, version: String = defaultVersion) =
            "io.arrow-kt:arrow-$module:$version"

        fun core(version: String = defaultVersion) =
            module("core", version)
    }
}