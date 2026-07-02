pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

// Source-linked `com.huanshankeji.base-settings-conventions` cannot be applied here:
// - Gradle does not expose buildSrc precompiled *settings* plugins to the main `settings.gradle.kts`
//   (only buildSrc *project* plugins are visible in `build.gradle.kts`).
// - `kotlin-common-gradle-plugins` is an included subproject, so `pluginManagement { includeBuild(...) }`
//   would conflict with `include(...)` (#54).
// `buildSrc/settings.gradle.kts` has the same limitation and applies foojay without a version because
// Gradle 9.6 bundles it for that nested settings build.
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "gradle-common"

include(
    "kotlin-common-gradle-plugins",
    "huanshankeji-team-gradle-plugins",
    "architecture-common-gradle-plugins",
    "common-gradle-dependencies",
)

// Bootstrap copy of `com.huanshankeji.setProjectConcatenatedNames`; the published API is in
// ConcatenatedProjectNames.kt but is not on the settings script classpath in this repo.
fun ProjectDescriptor.setProjectConcatenatedNames(prefix: String) {
    name = prefix + name
    for (child in children)
        child.setProjectConcatenatedNames("$name-")
}
rootProject.setProjectConcatenatedNames("")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        //mavenLocal() // no longer needed since no bootstrapping dependencies
        gradlePluginPortal()
        //mavenCentral() // originally added for Dokka but no longer needed as tested
    }
}
