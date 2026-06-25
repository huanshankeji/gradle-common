// `buildSrc` is a multi-project build whose subprojects source-link the corresponding root
// modules' sources, so that the build logic is compiled from the current source instead of
// depending on stale released versions of this repository's plugins (#54). Mirroring the root
// module structure (rather than merging everything into one compilation) preserves the
// project/binary boundaries that the precompiled script plugins' type-safe accessors rely on.

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
