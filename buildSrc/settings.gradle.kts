/*
`buildSrc` is a multi-project build whose subprojects source-link the corresponding root
modules' sources, so that the build logic is compiled from the current source instead of
depending on stale released versions of this repository's plugins (#54). Mirroring the root
module structure (rather than merging everything into one compilation) preserves the
project/binary boundaries that the precompiled script plugins' type-safe accessors rely on.
*/

plugins {
    /*
    Keep in sync with `com.huanshankeji.CommonVersions.kotlin` (and `[versions] kotlin` in
    `gradle/libs.versions.toml` when that entry is uncommented).
    */
    // https://kotlinlang.org/docs/releases.html
    kotlin("jvm") version "2.4.0" apply false
    // Bundled with Gradle 9.6+; version omitted to avoid conflicting with the embedded plugin.
    id("org.gradle.toolchains.foojay-resolver-convention")
}

// alternative approach
/*
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", "2.4.0"))
    }
}
*/

// The explanation below was written by Cursor and is not verified to be absolutely correct.
/*
`pluginManagement { plugins { kotlin("jvm") version … } }` alone is not sufficient: it constrains
plugin-id resolution for the `plugins {}` DSL but does not add `kotlin-gradle-plugins-bom` to the
build classpath, so versionless `org.jetbrains.kotlin:*` implementation dependencies still resolve
to `kotlin-dsl`'s embedded BOM.
*/
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
