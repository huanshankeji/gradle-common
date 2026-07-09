rootProject.name = "gradle-common"

plugins {
    // This version should be kept in sync with the one in `libs.versions.toml`.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include("common-gradle-dependencies")

include(
    "kotlin-common:gradle-library",
    "kotlin-common:project-gradle-plugins",
    "kotlin-common:settings-gradle-plugins",
)
project(":kotlin-common:gradle-library").name = "kotlin-common-gradle-library"
project(":kotlin-common:project-gradle-plugins").name = "kotlin-common-project-gradle-plugins"
project(":kotlin-common:settings-gradle-plugins").name = "kotlin-common-settings-gradle-plugins"

include(
    "huanshankeji-team:gradle-library",
    "huanshankeji-team:project-gradle-plugins",
    "huanshankeji-team:settings-gradle-plugins",
)

include("architecture-common-gradle-plugins")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        //mavenLocal() // no longer needed since no bootstrapping dependencies
        gradlePluginPortal()
        //mavenCentral() // originally added for Dokka but no longer needed as tested
    }
}
