package com.huanshankeji

import org.gradle.api.artifacts.dsl.DependencyHandler

// some but not all default dependencies
class CommonDependencies(val versions: CommonVersions = CommonVersions()) {
    interface SubgroupWithNameInArtifact {
        val groupPrefix: String
        val subgroupName: String
        val defaultVersion: String
        fun module(module: String, version: String = defaultVersion) =
            "$groupPrefix.$subgroupName:$subgroupName-$module:$version"
    }

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
        fun reflect(version: String = defaultVersion) = module("reflect", version)

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
        // TODO can be refactored with `SubgroupWithNameInArtifact`

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

        inner class Benchmark internal constructor() {
            val defaultVersion = versions.kotlinxBenchmark
            fun module(module: String, version: String = defaultVersion) =
                kotlinx("benchmark-$module", version)

            fun runtime(version: String = defaultVersion) =
                module("runtime", version)
        }

        val benchmark = Benchmark()
    }

    val kotlinx = Kotlinx()


    // official libraries from JetBrains

    // TODO can be refactored with `SubgroupWithNameInArtifact`
    inner class Exposed internal constructor() {
        val defaultVersion = versions.exposed
        fun module(module: String, version: String = defaultVersion) =
            "org.jetbrains.exposed:exposed-$module:$version"

        fun core(version: String = defaultVersion) =
            module("core", version)
    }

    val exposed = Exposed()

    // TODO can be refactored with `SubgroupWithNameInArtifact`
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

    // TODO consider refactoring other inner classes to this architecture too

    class JetbrainsAndroidx(defaultVersions: CommonVersions.JetbrainsAndroidx) {
        interface Subgroup : SubgroupWithNameInArtifact {
            override val groupPrefix: String get() = "org.jetbrains.androidx"
        }

        class Lifecycle(override val defaultVersion: String) : Subgroup {
            override val subgroupName: String get() = "lifecycle"

            fun viewmodel(version: String = defaultVersion) =
                module("viewmodel", version)

            fun viewmodelCompose(version: String = defaultVersion) =
                module("viewmodel-compose", version)
        }

        val lifecycle = Lifecycle(defaultVersions.lifecycle)

        class Navigation(override val defaultVersion: String) : Subgroup {
            override val subgroupName: String get() = "navigation"

            fun runtime(version: String = defaultVersion) =
                module("runtime", version)

            fun compose(version: String = defaultVersion) =
                module("compose", version)
        }

        val navigation = Navigation(defaultVersions.navigation)
    }

    val jetbrainsAndroidx = JetbrainsAndroidx(versions.jetBrainsAndroidx)

    class Androidx(defaultVersions: CommonVersions.Androidx) {
        companion object {
            val `package` = "androidx"
        }

        interface Subgroup : SubgroupWithNameInArtifact {
            override val groupPrefix get() = `package`
        }

        class Activity(override val defaultVersion: String) : Subgroup {
            override val subgroupName get() = "activity"

            fun compose(version: String = defaultVersion) =
                module("compose", version)
        }

        val activity = Activity(defaultVersions.activity)

        class Compose(defaultVersions: CommonVersions.Androidx.Compose) {
            interface Subgroup : SubgroupWithNameInArtifact {
                override val groupPrefix get() = "$`package`.compose"
            }

            class Ui(override val defaultVersion: String) : Subgroup {
                override val subgroupName get() = "ui"
            }

            val ui = Ui(defaultVersions.common)
        }

        val compose = Compose(defaultVersions.compose)
    }

    val androidx = Androidx(versions.androidx)


    // others

    // TODO can be refactored with `SubgroupWithNameInArtifact`
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

    // TODO can be refactored with `SubgroupWithNameInArtifact`
    inner class Kotest internal constructor() {
        val defaultVersion = versions.kotest
        fun module(module: String, version: String = defaultVersion) =
            "io.kotest:kotest-$module:$version"

        fun property(version: String = defaultVersion) =
            module("property", version)

        // TODO add the Gradle plugin (https://plugins.gradle.org/plugin/io.kotest), `kotest-framework-engine`, and `io.kotest:kotest-extensions-testcontainers`
    }

    val kotest = Kotest()

    fun postgreSql(version: String = versions.postgreSql) =
        "org.postgresql:postgresql:$version"

    // TODO can be refactored with `SubgroupWithNameInArtifact`
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

    inner class Testcontainers internal constructor() {
        val defaultVersion = versions.testcontainers
        fun moduleWithoutVersion(module: String) =
            "org.testcontainers:$module"

        fun moduleWithVersion(module: String, version: String = defaultVersion) =
            "${moduleWithoutVersion(module)}:$version"

        fun DependencyHandler.platformBom(version: String = defaultVersion) =
            platform(moduleWithVersion("testcontainers-bom", version))

        val testcontainers = moduleWithoutVersion("testcontainers")
        val testcontainersJunitJupiter = moduleWithoutVersion("testcontainers-junit-jupiter")
        val testcontainersPostgresql = moduleWithoutVersion("testcontainers-postgresql")
    }

    val testcontainers = Testcontainers()
}