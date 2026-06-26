// `buildSrc` is a multi-project build whose subprojects source-link the corresponding root
// modules' sources, so that the build logic is compiled from the current source instead of
// depending on stale released versions of this repository's plugins (#54). Mirroring the root
// module structure (rather than merging everything into one compilation) preserves the
// project/binary boundaries that the precompiled script plugins' type-safe accessors rely on.

// The Kotlin Gradle plugin version for build-logic subprojects is registered in
// `buildSrc/build.gradle.kts` (`alias(libs.plugins.kotlin.jvmWithExplicitVersion) apply false`).
//
// `pluginManagement { plugins { … } }` here is not sufficient on its own: it only constrains plugin
// id resolution for the `plugins {}` DSL, while versionless `org.jetbrains.kotlin:*` catalog
// library dependencies still follow the `kotlin-dsl` embedded Kotlin BOM (2.3.21 on Gradle 9.6)
// until subprojects apply `kotlin("jvm")` against a plugin classpath that includes Kotlin 2.4.0.
// The commented block below was an earlier attempt.
/*
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "2.4.0"
    }
}
*/

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        gradlePluginPortal()
    }
    // Register the root build's shared version catalog so the `buildSrc` build scripts can
    // reference the same dependency versions/coordinates as the root build (#54).
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include(
    "common-gradle-dependencies",
    "kotlin-common-gradle-plugins",
    "huanshankeji-team-gradle-plugins",
)
