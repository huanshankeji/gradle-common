/*
`buildSrc` is a multi-project build whose subprojects source-link the corresponding root
modules' sources, so that the build logic is compiled from the current source instead of
depending on stale released versions of this repository's plugins (#54). Mirroring the root
module structure (rather than merging everything into one compilation) preserves the
project/binary boundaries that the precompiled script plugins' type-safe accessors rely on.

Settings plugin modules are not source-linked here: the root build keeps the Foojay resolver
convention applied directly in `settings.gradle.kts` instead.
*/

plugins {
    /*
    Keep in sync with `com.huanshankeji.CommonVersions.kotlin` (and `[versions] kotlin` in
    `gradle/libs.versions.toml` when that entry is uncommented).
    */
    // https://kotlinlang.org/docs/releases.html
    kotlin("jvm") version "2.4.0" apply false
}

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
    "kotlin-common-gradle-library",
    "kotlin-common-project-gradle-plugins",
    "huanshankeji-team:gradle-library",
    "huanshankeji-team:project-gradle-plugins",
)
