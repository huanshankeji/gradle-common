// `buildSrc` is a multi-project build whose subprojects source-link the corresponding root
// modules' sources, so that the build logic is compiled from the current source instead of
// depending on stale released versions of this repository's plugins (#54). Mirroring the root
// module structure (rather than merging everything into one compilation) preserves the
// project/binary boundaries that the precompiled script plugins' type-safe accessors rely on.

// The Kotlin Gradle plugin version for build-logic subprojects is registered in
// `buildSrc/build.gradle.kts` (`alias(libs.plugins.kotlin.jvmWithExplicitVersion) apply false`).
//
// `pluginManagement { plugins { … } }` here is not sufficient on its own. It constrains plugin-id
// resolution for the `plugins {}` DSL (so `--info` logs show `org.jetbrains.kotlin.jvm` at 2.4.0),
// but versionless `org.jetbrains.kotlin:*` `implementation` dependencies (from the catalog) are
// aligned by the `kotlin-gradle-plugins-bom` platform, not by `pluginManagement`. With only
// `pluginManagement`, `kotlin-dsl`'s embedded Kotlin BOM (2.3.21 on Gradle 9.6) still wins, so
// `kotlin-compiler-embeddable` and `kotlin-gradle-plugin` stay on 2.3.21. Registering the plugin
// with `apply false` in the root `buildSrc` build script adds `kotlin-gradle-plugins-bom:2.4.0`
// to the build classpath and aligns those library dependencies. The block below was an earlier
// attempt; keep it commented out so the BOM is not pinned twice.
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
