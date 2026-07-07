rootProject.name = "gradle-common"

plugins {
    // This version should be kept in sync with the one in `libs.versions.toml`.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include("kotlin-common-gradle-plugins")
include("kotlin-common-settings-gradle-plugins")
include("huanshankeji-team-gradle-plugins")
project(":huanshankeji-team-gradle-plugins").name = "gradle-plugins"
include("huanshankeji-team-settings-gradle-plugins")
project(":huanshankeji-team-settings-gradle-plugins").name = "team-settings-gradle-plugins"
include("architecture-common-gradle-plugins")
include("common-gradle-dependencies")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        //mavenLocal() // no longer needed since no bootstrapping dependencies
        gradlePluginPortal()
        //mavenCentral() // originally added for Dokka but no longer needed as tested
    }
}
