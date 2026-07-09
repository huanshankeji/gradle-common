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

/*
Team subprojects use `group = "team"` and the `:team:*` project path prefix (see their
`build.gradle.kts` files) so their simple child names do not share coordinates with
kotlin-common modules. kotlin-common still uses CPN child names (`kotlin-common-gradle-library`,
…) for the same reason on its side.
*/
include(
    "common-gradle-dependencies",
    "kotlin-common:kotlin-common-gradle-library",
    "kotlin-common:kotlin-common-project-gradle-plugins",
    "team:gradle-library",
    "team:project-gradle-plugins",
)

project(":kotlin-common:kotlin-common-gradle-library").projectDir = file("kotlin-common/gradle-library")
project(":kotlin-common:kotlin-common-project-gradle-plugins").projectDir = file("kotlin-common/project-gradle-plugins")
project(":team:gradle-library").projectDir = file("huanshankeji-team/gradle-library")
project(":team:project-gradle-plugins").projectDir = file("huanshankeji-team/project-gradle-plugins")
