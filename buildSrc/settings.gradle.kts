/*
`buildSrc` is a multi-project build whose subprojects source-link the corresponding root
modules' sources, so that the build logic is compiled from the current source instead of
depending on stale released versions of this repository's plugins. Mirroring the root
module structure (rather than merging everything into one compilation) preserves the
project/binary boundaries that the precompiled script plugins' type-safe accessors rely on.
*/

plugins {
    /*
    Keep in sync with `[versions] kotlin` in `gradle/libs.versions.toml` (and therefore
    `CommonVersions.kotlin` via `GeneratedVersions`).
    */
    // https://kotlinlang.org/docs/releases.html
    kotlin("jvm") version "2.4.0" apply false
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
    // reference the same dependency versions/coordinates as the root build.
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

/*
kotlin-common subprojects use CPN child names (`kotlin-common-gradle-library`, …), not simple
names (`gradle-library`, `project-gradle-plugins`). huanshankeji-team already uses those
simple names under its own parent (`:huanshankeji-team:gradle-library`, …). Reusing the same
child names under `:kotlin-common:` would give modules the same logical coordinates in the
buildSrc project tree; precompiled-script accessor generation then fails to load cross-module
kotlin-common helpers (e.g. `GithubPackagesMavenCredentials` from gradle-library) when team
scripts apply kotlin-common plugins.
*/
include(
    "common-gradle-dependencies",
    "kotlin-common:gradle-library",
    "kotlin-common:project-gradle-plugins",
    "huanshankeji-team:gradle-library",
    "huanshankeji-team:project-gradle-plugins",
)

project(":kotlin-common:gradle-library").name = "kotlin-common-gradle-library"
project(":kotlin-common:project-gradle-plugins").name = "kotlin-common-project-gradle-plugins"
